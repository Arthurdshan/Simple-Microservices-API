package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.dtos.SupplierRequest;
import com.arthurhan.productapi.dtos.SupplierResponse;
import com.arthurhan.productapi.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/supplier")
public class SupplierController
{
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> save(@RequestBody SupplierRequest request)
    {
        SupplierResponse response = supplierService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
