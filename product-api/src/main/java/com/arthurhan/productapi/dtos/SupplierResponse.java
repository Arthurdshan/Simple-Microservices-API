package com.arthurhan.productapi.dtos;

import com.arthurhan.productapi.models.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse
{
    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier)
    {
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);

        return response;
    }
}
