package com.arthurhan.productapi.dtos;

import com.arthurhan.productapi.enums.SalesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesConfirmationDTO
{
    private String salesId;
    private SalesStatus status;
}
