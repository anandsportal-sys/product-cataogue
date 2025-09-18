package com.example.productcatalogue.service;

import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String search, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSkuContainingIgnoreCaseOrBrandContainingIgnoreCase(
            search, search, search, search, search, pageable
        );
    }

    public void generateProducts(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Product p = new Product(
                "Product " + UUID.randomUUID().toString().substring(0, 8),
                "Description " + UUID.randomUUID().toString().substring(0, 8),
                "Category " + (i % 10),
                "SKU" + UUID.randomUUID().toString().substring(0, 8),
                "Brand " + (i % 5)
            );
            products.add(p);
        }
        productRepository.saveAll(products);
    }
}
