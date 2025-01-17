package com.mrk.product_service.dto;

import com.mrk.product_service.entities.Product;
import lombok.Data;

@Data // Lombok annotation to generate getter, setter, toString, equals, and hashcode methods
public class ProductResponseWithCategory {

    private Product product;
    private Object category; // You can change 'Object' to a specific category type if needed
}
