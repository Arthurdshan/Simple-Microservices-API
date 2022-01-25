package com.arthurhan.productapi.models;

import com.arthurhan.productapi.dtos.ProductRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @NotNull
    private Integer availableQuantity;

    @Column(updatable = false)
    private Instant createdAt;

    public static Product of(ProductRequest request,
                             Category category,
                             Supplier supplier)
    {
        return Product.builder()
                .name(request.getName())
                .category(category)
                .supplier(supplier)
                .availableQuantity(request.getAvailableQuantity())
                .createdAt(Instant.now())
                .build();
    }
}
