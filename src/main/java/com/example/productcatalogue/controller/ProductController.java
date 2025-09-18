package com.example.productcatalogue.controller;

import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
}
