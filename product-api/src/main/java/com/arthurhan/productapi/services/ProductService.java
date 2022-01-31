package com.arthurhan.productapi.services;

import com.arthurhan.productapi.client.SalesClient;
import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.*;
import com.arthurhan.productapi.enums.SalesStatus;
import com.arthurhan.productapi.exception.CrudException;
import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.models.Category;
import com.arthurhan.productapi.models.Product;
import com.arthurhan.productapi.models.Supplier;
import com.arthurhan.productapi.rabbitmq.SalesConfirmationSender;
import com.arthurhan.productapi.repositories.CategoryRepository;
import com.arthurhan.productapi.repositories.ProductRepository;
import com.arthurhan.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService
{
    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

    private void validateRequest(ProductRequest request)
    {
        if (request.getName() == null || request.getName().isBlank())
        {
            throw new ValidationException("Product name not informed");
        }

        if (request.getAvailableQuantity() == null || request.getAvailableQuantity() < ZERO)
        {
            throw new ValidationException("Review the product quantity");
        }

        if (request.getCategoryId() == null)
        {
            throw new ValidationException("The category Id was not informed");
        }

        if (request.getSupplierId() == null)
        {
            throw new ValidationException("The supplier Id was not informed");
        }
    }

    public ProductResponse save(ProductRequest request)
    {
        validateRequest(request);

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new ValidationException("No category for the given ID"));

        Supplier supplier = supplierRepository
                .findById(request.getSupplierId())
                .orElseThrow(() -> new ValidationException("No supplier for the given ID"));

        Product product = productRepository.saveAndFlush(Product.of(request, category, supplier));

        return ProductResponse.of(product);
    }

    public ProductResponse findById(Integer id)
    {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("Product not found"));

        return ProductResponse.of(product);
    }

    public List<ProductResponse> findAll()
    {
        return productRepository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name)
    {
        if (name == null || name.isBlank())
        {
            throw new ValidationException("Name must be informed");
        }

        return productRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("Category id must be informed");
        }

        return productRepository
                .findByCategoryId(id)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("Supplier id must be informed");
        }

        return productRepository
                .findBySupplierId(id)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public Boolean existsByCategoryId(Integer id)
    {
        return productRepository.existsByCategoryId(id);
    }

    public Boolean existsBySupplierId(Integer id)
    {
        return productRepository.existsBySupplierId(id);
    }

    public StatusResponse delete(Integer id)
    {
        try
        {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e)
        {
            throw new CrudException("No product was found for the given Id");
        }

        return new StatusResponse(HttpStatus.OK.value(), "The product was deleted");
    }

    public ProductResponse update(ProductRequest request,
                                  Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("Id must be informed");
        }

        validateRequest(request);

        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        Optional<Supplier> supplier = supplierRepository.findById(request.getSupplierId());

        Product product = Product.of(
                request,
                category.orElseThrow(() -> new CrudException("Category not found")),
                supplier.orElseThrow(() -> new CrudException("Supplier not found")));

        product.setId(id);

        productRepository.saveAndFlush(product);

        return ProductResponse.of(product);
    }

    @Transactional
    public void updateProductStock(ProductStockDTO dto)
    {
        try
        {
            validateStockUpdateData(dto);

            List<Product> productsForUpdate = new ArrayList<>();

            dto.getProducts()
                    .forEach(salesProduct -> {
                        Product existingProduct = productRepository
                                .findById(salesProduct.getProductId())
                                .orElseThrow(() -> new ValidationException("Product with Id " + salesProduct.getProductId() + " was not found"));

                        if (salesProduct.getQuantity() > existingProduct.getAvailableQuantity() || salesProduct.getQuantity() < 1)
                        {
                            throw new ValidationException("The product stock can't be updated");
                        }

                        existingProduct.updateStock(salesProduct.getQuantity());
                        productsForUpdate.add(existingProduct);
                    });

            if (!productsForUpdate.isEmpty())
            {
                productRepository.saveAll(productsForUpdate);
                SalesConfirmationDTO approvedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED);
                salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
            }
        } catch (Exception e)
        {
            salesConfirmationSender.
                    sendSalesConfirmationMessage(
                            new SalesConfirmationDTO(
                                    dto.getSalesId(),
                                    SalesStatus.REJECTED));
        }
    }

    private void validateStockUpdateData(ProductStockDTO dto)
    {
        if (dto == null
                || dto.getSalesId() == null)
        {
            throw new ValidationException("The product data or sales ID must be informed");
        }

        if (dto.getProducts() == null || dto.getProducts().isEmpty())
        {
            throw new ValidationException("The sale products must be informed");
        }

        dto
                .getProducts()
                .forEach(salesProduct -> {
                    if (salesProduct.getQuantity() == null
                            || salesProduct.getProductId() == null)
                    {
                        throw new ValidationException("Product Id or quantity is invalid");
                    }
                });
    }

    public ProductSalesResponse findProductSales(Integer id)
    {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("No product found"));

        try
        {
            SalesProductResponse sales = salesClient
                    .findSalesByProductId(product.getId())
                    .orElseThrow(() -> new ValidationException("The sales were not found"));

            return ProductSalesResponse.of(product, sales.getSalesId());
        } catch (Exception e)
        {
            throw new ValidationException("There was an error trying to get the product sales");
        }
    }

    public StatusResponse checkProductStock(ProductCheckStockRequest request)
    {
        if (request == null || request.getProducts() == null || request.getProducts().isEmpty())
        {
            throw new ValidationException("The request data must not be null");
        }

        request
                .getProducts()
                .forEach(productQuantity -> {
                    if (productQuantity.getProductId() == null
                            || productQuantity.getQuantity() == null)
                    {
                        throw new ValidationException("Product ID and quantity must be informed");
                    }

                    Product product = productRepository
                            .findById(productQuantity.getProductId())
                            .orElseThrow(() -> new ValidationException("Product with ID " + productQuantity.getProductId() + " was not found"));

                    if (product.getAvailableQuantity() < productQuantity.getQuantity())
                    {
                        throw new ValidationException("The product " + product.getName() + " is out of stock.");
                    }
                });

        return new StatusResponse(HttpStatus.OK.value(), "Stock ok!");
    }
}
