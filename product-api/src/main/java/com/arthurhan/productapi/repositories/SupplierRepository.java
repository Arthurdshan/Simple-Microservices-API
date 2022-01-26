package com.arthurhan.productapi.repositories;

import com.arthurhan.productapi.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>
{
    List<Supplier> findByNameIgnoreCaseContaining(String name);
}
