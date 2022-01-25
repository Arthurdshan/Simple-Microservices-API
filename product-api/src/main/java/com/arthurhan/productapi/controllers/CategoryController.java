package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.CategoryResponse;
import com.arthurhan.productapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request)
    {
        CategoryResponse response = categoryService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
