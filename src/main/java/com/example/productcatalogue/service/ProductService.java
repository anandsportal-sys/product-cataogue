package com.example.productcatalogue.service;

import com.example.productcatalogue.exception.DuplicateResourceException;
import com.example.productcatalogue.exception.ResourceNotFoundException;
import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        try {
            logger.info("Fetching all products with pagination");
            return productRepository.findAll(pageable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching all products", e);
            throw new RuntimeException("Failed to retrieve products", e);
        }
    }

    public Page<Product> searchProducts(String search, Pageable pageable) {
        try {
            if (search == null || search.trim().isEmpty()) {
                logger.warning("Search term is empty, returning all products");
                return getAllProducts(pageable);
            }

            logger.info("Searching products with term: " + search);
            return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSkuContainingIgnoreCaseOrBrandContainingIgnoreCaseOrPriceContainingIgnoreCase(
                search, search, search, search, search, search, pageable
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching products with term: " + search, e);
            throw new RuntimeException("Failed to search products", e);
        }
    }

    public void generateProducts(int count) {
        try {
            if (count <= 0) {
                logger.warning("Invalid product count: " + count);
                throw new IllegalArgumentException("Product count must be greater than zero");
            }

            if (count > 10000) {
                logger.warning("Excessive product count requested: " + count);
                throw new IllegalArgumentException("Cannot generate more than 10,000 products at once");
            }

            logger.info("Generating " + count + " products");
            List<Product> products = new ArrayList<>();
            Random random = new Random();
            DecimalFormat df = new DecimalFormat("#.00");

            String[] names = {"Laptop", "Smartphone", "Headphones", "Monitor", "Keyboard", "Mouse", "Tablet", "Camera", "Printer", "Speaker"};
            String[] descriptions = {
                "High performance", "Budget friendly", "Latest model", "Compact design", "Wireless", "Ergonomic", "Portable", "HD quality", "All-in-one", "Bluetooth enabled"
            };
            String[] categories = {"Electronics", "Computers", "Accessories", "Audio", "Office", "Mobile", "Photography"};
            String[] brands = {"Apple", "Samsung", "Sony", "Dell", "HP", "Lenovo", "Canon", "Logitech", "Bose", "Asus"};

            for (int i = 0; i < count; i++) {
                String name = names[i % names.length];
                String description = descriptions[i % descriptions.length];
                String category = categories[i % categories.length];
                String brand = brands[i % brands.length];
                String sku = category.substring(0, 3).toUpperCase() + "-" + (1000 + i);

                // Convert price to String with proper formatting
                String price = "$" + df.format(50 + (random.nextInt(950)));

                Product p = new Product(
                    name,
                    description,
                    category,
                    sku,
                    brand,
                    price
                );
                products.add(p);
            }
            productRepository.saveAll(products);
            logger.info("Successfully generated " + count + " products");
        } catch (IllegalArgumentException e) {
            // Re-throw validation exceptions
            logger.log(Level.WARNING, "Validation error while generating products", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating products", e);
            throw new RuntimeException("Failed to generate products", e);
        }
    }

    /**
     * Get product by ID
     */
    public Product getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    /**
     * Create a new product
     */
    public Product createProduct(Product product) {
        validateProduct(product);

        // Check if product with same name already exists
        Optional<Product> existingProduct = productRepository.findByNameIgnoreCase(product.getName());
        if (existingProduct.isPresent()) {
            throw new DuplicateResourceException("Product", "name", product.getName());
        }

        return productRepository.save(product);
    }

    /**
     * Update an existing product
     */
    public Product updateProduct(Long id, Product productDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        validateProduct(productDetails);

        Product existingProduct = getProductById(id);

        // Check if another product with same name exists (excluding current product)
        Optional<Product> duplicateProduct = productRepository.findByNameIgnoreCase(productDetails.getName());
        if (duplicateProduct.isPresent() && !duplicateProduct.get().getId().equals(id)) {
            throw new DuplicateResourceException("Product", "name", productDetails.getName());
        }

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());

        return productRepository.save(existingProduct);
    }

    /**
     * Delete a product
     */
    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        Product product = getProductById(id);
        productRepository.delete(product);
    }

    /**
     * Get products by category
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        List<Product> products = productRepository.findByCategory(category.trim());
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category: " + category);
        }

        return products;
    }

    /**
     * Check if product exists
     */
    public boolean existsById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return productRepository.existsById(id);
    }

    /**
     * Get total count of products
     */
    public long getTotalCount() {
        return productRepository.count();
    }

    /**
     * Validate product data
     */
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        if (product.getPrice() == null || product.getPrice().trim().isEmpty()) {
            throw new IllegalArgumentException("Product price must not be empty");
        }

        if (product.getName().length() > 255) {
            throw new IllegalArgumentException("Product name cannot exceed 255 characters");
        }

        if (product.getDescription() != null && product.getDescription().length() > 1000) {
            throw new IllegalArgumentException("Product description cannot exceed 1000 characters");
        }
    }
}
