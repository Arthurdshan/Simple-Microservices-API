package com.arthurhan.productapi.services;

import com.arthurhan.productapi.config.StatusResponse;
import com.arthurhan.productapi.dtos.SupplierRequest;
import com.arthurhan.productapi.dtos.SupplierResponse;
import com.arthurhan.productapi.exception.DeleteException;
import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.models.Supplier;
import com.arthurhan.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService
{
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    private void validateRequest(SupplierRequest request)
    {
        if (request.getName() == null || request.getName().isBlank())
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

    public SupplierResponse findById(Integer id)
    {
        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("Supplier not found"));

        return SupplierResponse.of(supplier);
    }

    public List<SupplierResponse> findAll()
    {
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name)
    {
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public StatusResponse delete(Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("Id must be informed");
        }

        if (productService.existsBySupplierId(id))
        {
            throw new ValidationException("You cannot delete this supplier.");
        }

        try
        {
            supplierRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e)
        {
            throw new DeleteException("No supplier was found for the given Id");
        }

        return new StatusResponse(HttpStatus.OK.value(), "The Supplier was deleted");
    }

    public SupplierResponse update(SupplierRequest request,
                                   Integer id)
    {
        if (id == null)
        {
            throw new ValidationException("Id must be informed");
        }

        validateRequest(request);

        Supplier supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.saveAndFlush(supplier);

        return SupplierResponse.of(supplier);
    }
}
