package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.dtos.ProductRequest;
import com.arthurhan.productapi.dtos.ProductResponse;
import com.arthurhan.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request)
    {
        ProductResponse response = productService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
