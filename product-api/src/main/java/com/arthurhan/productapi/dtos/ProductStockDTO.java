package com.arthurhan.productapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDTO
{
    private String salesId;
    private List<ProductQuantityDTO> products;
}
