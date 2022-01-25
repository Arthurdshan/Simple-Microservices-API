package com.arthurhan.productapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest
{
    private String name;
    private Integer availableQuantity;
    private Integer supplierId;
    private Integer categoryId;
}
