package com.najmi.shop.product;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public record ProductController(ProductService productService) {


    @GetMapping
    public ResponseEntity<Page<ProductModel>> list() {
        return ResponseEntity.ok(productService.list());
    }

    @GetMapping("/{id}")
    public ProductModel find(@PathVariable Long id) {
        return productService.find(id);
    }

    @PostMapping
    public ProductModel create(@RequestBody ProductModel productModel) {
        return productService.create(productModel);
    }
}
