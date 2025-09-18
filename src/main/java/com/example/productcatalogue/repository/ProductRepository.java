package com.example.productcatalogue.repository;

import com.example.productcatalogue.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSkuContainingIgnoreCaseOrBrandContainingIgnoreCase(
        String name, String description, String category, String sku, String brand, Pageable pageable
    );
}

