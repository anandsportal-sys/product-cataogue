package com.example.productcatalogue.repository;

import com.example.productcatalogue.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Now price is already a String, so we can just use it directly in the method name
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSkuContainingIgnoreCaseOrBrandContainingIgnoreCaseOrPriceContainingIgnoreCase(
        String name, String description, String category, String sku, String brand, String price, Pageable pageable
    );

    // Find products by name (case-insensitive search)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find by exact name (case-insensitive)
    Optional<Product> findByNameIgnoreCase(String name);

    // Find products by category
    List<Product> findByCategory(String category);

    // Find top N expensive products
    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> findTopExpensiveProducts(Pageable pageable);

}
