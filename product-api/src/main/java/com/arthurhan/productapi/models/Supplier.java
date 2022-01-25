package com.arthurhan.productapi.models;

import com.arthurhan.productapi.dtos.SupplierRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Supplier
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Set<Product> products = new HashSet<>();

    public static Supplier of(SupplierRequest request)
    {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(request, supplier);

        return supplier;
    }
}
