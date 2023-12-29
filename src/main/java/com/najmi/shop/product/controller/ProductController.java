package com.najmi.shop.product.controller;

import com.najmi.shop.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public record ProductController(ProductService productService) {


    @GetMapping
    public List<ProductModel> list() throws Exception {
        return productService.list();
    }

    @GetMapping("/{id}")
    public ProductModel find(@PathVariable Integer id) throws Exception {
        return productService.find(id);
    }

    @PostMapping
    public ProductModel create(@RequestBody ProductModel productModel) throws Exception {
        return productService.create(productModel);
    }
}
