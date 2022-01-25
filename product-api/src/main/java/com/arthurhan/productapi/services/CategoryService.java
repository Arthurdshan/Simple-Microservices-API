package com.arthurhan.productapi.services;

import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.CategoryResponse;
import com.arthurhan.productapi.models.Category;
import com.arthurhan.productapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    private void validateRequest(CategoryRequest request)
    {
        if(request.getDescription() == null || request.getDescription().isBlank())
        {
            throw new ValidationException("Category description not informed");
        }
    }

    public CategoryResponse save(CategoryRequest request)
    {
        validateRequest(request);

        Category category = categoryRepository.saveAndFlush(Category.of(request));

        return CategoryResponse.of(category);
    }

    public List<CategoryResponse> findAll()
    {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("ID must be informed");
        }

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("No category for the given ID"));

        return CategoryResponse.of(category);
    }
}
