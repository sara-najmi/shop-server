package com.najmi.shop.product.service;

import com.najmi.shop.product.controller.ProductModel;
import com.najmi.shop.product.orm.Product;
import com.najmi.shop.product.orm.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public Page<ProductModel> list() throws Exception {
        return productRepository
                .findAll(PageRequest.of(0, 100))
                .map(ProductServiceImpl::convertToModel);
    }

    @Override
    public ProductModel find(Integer id) throws Exception {
        return convertToModel(productRepository.findById(id).orElseThrow());
    }

    @Override
    public ProductModel create(ProductModel productModel) throws Exception {
        return convertToModel(productRepository.save(convertToEntity(productModel)));
    }


    public static Product convertToEntity(ProductModel productModel) {
        var product = new Product();
        BeanUtils.copyProperties(productModel, product);
        return product;
    }

    public static ProductModel convertToModel(Product product) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(product, productModel);
        return productModel;
    }
}
