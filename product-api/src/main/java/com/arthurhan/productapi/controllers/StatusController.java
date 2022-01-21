package com.arthurhan.productapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatusController
{
    @GetMapping(value = "/status")
    public ResponseEntity<String> getApiStatus()
    {
        String response = "Product API working";

        return ResponseEntity.ok().body(response);
    }
}
