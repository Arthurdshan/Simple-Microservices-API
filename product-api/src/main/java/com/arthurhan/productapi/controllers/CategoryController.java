package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.CategoryResponse;
import com.arthurhan.productapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll()
    {
        List<CategoryResponse> response = categoryService.findAll();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Integer id)
    {
        CategoryResponse response = categoryService.findById(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
