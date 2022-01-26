package com.arthurhan.productapi.services;

import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.CategoryResponse;
import com.arthurhan.productapi.dtos.ProductRequest;
import com.arthurhan.productapi.dtos.ProductResponse;
import com.arthurhan.productapi.exception.DeleteException;
import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.models.Category;
import com.arthurhan.productapi.models.Product;
import com.arthurhan.productapi.models.Supplier;
import com.arthurhan.productapi.repositories.CategoryRepository;
import com.arthurhan.productapi.repositories.ProductRepository;
import com.arthurhan.productapi.repositories.SupplierRepository;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
            throw new DeleteException("No product was found for the given Id");
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

        Product product = Product.of(request, category.orElse(null), supplier.orElse(null));
        product.setId(id);

        productRepository.saveAndFlush(product);

        return ProductResponse.of(product);
    }
}
