package com.arthurhan.productapi.controllers;

import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.*;
import com.arthurhan.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll()
    {
        List<ProductResponse> response = productService.findAll();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Integer id)
    {
        ProductResponse response = productService.findById(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable String name)
    {
        List<ProductResponse> response = productService.findByName(name);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<List<ProductResponse>> findByCategoryId(@PathVariable Integer id)
    {
        List<ProductResponse> response = productService.findByCategoryId(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/supplier/{id}")
    public ResponseEntity<List<ProductResponse>> findBySupplierId(@PathVariable Integer id)
    {
        List<ProductResponse> response = productService.findBySupplierId(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> deleteById(@PathVariable Integer id)
    {
        StatusResponse status = productService.delete(id);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> update(@RequestBody ProductRequest request,
                                                  @PathVariable Integer id)
    {
        ProductResponse response = productService.update(request, id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/check_stock")
    public ResponseEntity<StatusResponse> checkProductStock(@RequestBody ProductCheckStockRequest request)
    {
        return new ResponseEntity<>(productService.checkProductStock(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/sales")
    public ResponseEntity<ProductSalesResponse> findProductSales(@PathVariable Integer id)
    {
        return new ResponseEntity<>(productService.findProductSales(id), HttpStatus.OK);
    }
}
