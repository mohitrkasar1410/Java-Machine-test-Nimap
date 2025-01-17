package com.mrk.product_service.controllers;

import com.mrk.product_service.entities.Product;
import com.mrk.product_service.services.ProductService;
import com.mrk.product_service.client.CategoryClient;
import com.mrk.product_service.dto.ProductResponseWithCategory; // Import the DTO class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@RestController
@RequestMapping("/apips/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryClient categoryClient;  // Inject the CategoryClient

    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page) {
        return productService.getAllProducts(PageRequest.of(page, 10));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        logger.info("Fetching category with ID: {}", product.getCategoryId());
        Object category = categoryClient.getCategoryById(product.getCategoryId());

        if (category == null) {
            String errorMessage = "Category not found with id: " + product.getCategoryId();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }

        logger.info("Category found: {}", category);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            // Fetch the category details
            Object category = categoryClient.getCategoryById(product.get().getCategoryId());

            if (category != null) {
                // Create a response object containing both product and category data
                ProductResponseWithCategory response = new ProductResponseWithCategory();
                response.setProduct(product.get());
                response.setCategory(category);

                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Category not found for product with id: " + id;
                logger.error(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(errorMessage);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.getProductById(id);

        if (existingProduct.isPresent()) {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + id);
        }
    }
}
