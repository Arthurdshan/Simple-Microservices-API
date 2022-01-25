package com.arthurhan.productapi.services;

import com.arthurhan.productapi.dtos.CategoryRequest;
import com.arthurhan.productapi.dtos.SupplierRequest;
import com.arthurhan.productapi.dtos.SupplierResponse;
import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.models.Supplier;
import com.arthurhan.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService
{
    @Autowired
    private SupplierRepository supplierRepository;

    private void validateRequest(SupplierRequest request)
    {
        if(request.getName() == null || request.getName().isBlank())
        {
            throw new ValidationException("Supplier name not informed");
        }
    }

    public SupplierResponse save(SupplierRequest request)
    {
        validateRequest(request);

        Supplier supplier = supplierRepository.saveAndFlush(Supplier.of(request));

        return SupplierResponse.of(supplier);
    }
}
