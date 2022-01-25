package com.arthurhan.productapi.services;

import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.ProductRequest;
import com.arthurhan.productapi.dtos.ProductResponse;
import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.models.Category;
import com.arthurhan.productapi.models.Product;
import com.arthurhan.productapi.models.Supplier;
import com.arthurhan.productapi.repositories.CategoryRepository;
import com.arthurhan.productapi.repositories.ProductRepository;
import com.arthurhan.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProductResponse save( ProductRequest request)
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
}
