package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.SupplierRequest;
import com.arthurhan.productapi.dtos.SupplierResponse;
import com.arthurhan.productapi.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<SupplierResponse> findById(@PathVariable Integer id)
    {
        SupplierResponse response = supplierService.findById(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> findAll()
    {
        List<SupplierResponse> response = supplierService.findAll();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<SupplierResponse>> findByName(@PathVariable String name)
    {
        List<SupplierResponse> response = supplierService.findByName(name);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> deleteById(@PathVariable Integer id)
    {
        StatusResponse response = supplierService.delete(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SupplierResponse> update(@RequestBody SupplierRequest request,
                                                   @PathVariable Integer id)
    {
        SupplierResponse response = supplierService.update(request, id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
