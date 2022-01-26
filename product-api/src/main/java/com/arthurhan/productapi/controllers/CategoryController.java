package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.CategoryResponse;
import com.arthurhan.productapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
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

    @GetMapping(value = "description/{description}")
    public ResponseEntity<List<CategoryResponse>> findByDescription(@PathVariable String description)
    {
        List<CategoryResponse> response = categoryService.findByDescription(description);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> deleteById(@PathVariable Integer id)
    {
        StatusResponse response = categoryService.delete(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody CategoryRequest request,
                                                   @PathVariable Integer id)
    {
        CategoryResponse response = categoryService.update(request, id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
