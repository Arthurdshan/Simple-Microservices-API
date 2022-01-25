package com.arthurhan.productapi.repositories;

import com.arthurhan.productapi.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>
{
}
