package com.arthurhan.productapi.models;

import com.arthurhan.productapi.dtos.CategoryRequest;
import com.sun.istack.NotNull;
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
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public static Category of(CategoryRequest request)
    {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);

        return category;
    }
}
