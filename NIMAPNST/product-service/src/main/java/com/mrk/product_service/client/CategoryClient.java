package com.mrk.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", url = "http://localhost:9090/apics")
public interface CategoryClient {

    @GetMapping("/categories/{id}")
    Object getCategoryById(@PathVariable("id") Long id);
}
