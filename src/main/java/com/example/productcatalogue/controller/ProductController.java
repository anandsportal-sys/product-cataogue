package com.example.productcatalogue.controller;

import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<Product> listProducts(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return productService.searchProducts(search, pageable);
        }
        return productService.getAllProducts(pageable);
    }

    @PostMapping("/generate")
    public String generateProducts(@RequestParam(value = "count", required = false, defaultValue = "1000") int count) {
        productService.generateProducts(count);
        return count + " products generated successfully.";
    }

    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct( @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable Long id,
        @RequestBody Product productDetails) {

        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Check if product exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> productExists(@PathVariable Long id) {
        boolean exists = productService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    /**
     * Get total count of products
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        long count = productService.getTotalCount();
        return ResponseEntity.ok(count);
    }
}
