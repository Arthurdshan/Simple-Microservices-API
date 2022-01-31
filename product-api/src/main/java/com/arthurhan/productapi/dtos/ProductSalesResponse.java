package com.arthurhan.productapi.dtos;

import com.arthurhan.productapi.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesResponse
{
    private Integer id;
    private String name;
    private Integer availableQuantity;
    private Instant createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private List<String> sales;

    public static ProductSalesResponse of(Product product, List<String> sales)
    {
        return ProductSalesResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .createdAt(product.getCreatedAt())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .sales(sales)
                .build();
    }
}
