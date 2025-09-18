package org.anand.productcatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.productcatalogue"})
@EnableJpaRepositories(basePackages = "com.example.productcatalogue.repository")
@EntityScan(basePackages = "com.example.productcatalogue.model")
public class ProductCatalogueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogueApplication.class, args);
    }

}
