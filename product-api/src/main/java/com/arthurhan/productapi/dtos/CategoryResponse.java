package com.arthurhan.productapi.dtos;

import com.arthurhan.productapi.models.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class CategoryResponse
{
    private Integer id;
    private String description;

    public static CategoryResponse of(Category category)
    {
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);

        return response;
    }
}
