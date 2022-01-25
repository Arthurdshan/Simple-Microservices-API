package com.arthurhan.productapi.dtos;

import com.arthurhan.productapi.models.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse
{
    private Integer id;
    private String name;
    private Integer availableQuantity;
    private Instant createdAt;
    private SupplierResponse supplierResponse;
    private CategoryResponse categoryResponse;

    public static ProductResponse of(Product product)
    {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .createdAt(product.getCreatedAt())
                .supplierResponse(SupplierResponse.of(product.getSupplier()))
                .categoryResponse(CategoryResponse.of(product.getCategory()))
                .build();
    }
}
