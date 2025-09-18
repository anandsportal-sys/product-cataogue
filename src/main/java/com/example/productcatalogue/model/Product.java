package com.example.productcatalogue.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private String sku;
    private String brand;
    private String price; // Changed from BigDecimal to String

    // Constructors
    public Product() {}

    public Product(String name, String description, String category, String sku, String brand, String price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.sku = sku;
        this.brand = brand;
        this.price = price;
    }

    // For backward compatibility in service
    public Product(String name, String description, String category, String sku, String brand) {
        this(name, description, category, sku, brand, "0.00");
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
