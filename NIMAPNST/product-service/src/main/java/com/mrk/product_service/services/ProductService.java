package com.mrk.product_service.services;

import com.mrk.product_service.entities.Product;
import com.mrk.product_service.repositories.ProductRepository;
import com.mrk.product_service.client.CategoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryClient categoryClient; // Inject the CategoryClient

    // Method to get all products with pagination
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Method to create a new product
    public Product createProduct(Product product) {
        // Optional: Check if the category exists using CategoryClient
        Object category = categoryClient.getCategoryById(product.getCategoryId());
        if (category == null) {
            throw new RuntimeException("Category not found with id: " + product.getCategoryId());
        }
        return productRepository.save(product);
    }

    // Method to get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Method to update product by ID
    public Product updateProduct(Long id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Optional: Validate category before updating the product
        Object category = categoryClient.getCategoryById(product.getCategoryId());
        if (category == null) {
            throw new RuntimeException("Category not found with id: " + product.getCategoryId());
        }

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        return productRepository.save(existing);
    }

    // Method to delete product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
