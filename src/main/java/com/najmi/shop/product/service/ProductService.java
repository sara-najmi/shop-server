package com.najmi.shop.product.service;

import com.najmi.shop.product.controller.ProductModel;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductModel> list() throws Exception;

    ProductModel find(Integer id) throws Exception;

    ProductModel create(ProductModel productModel) throws Exception;



}
