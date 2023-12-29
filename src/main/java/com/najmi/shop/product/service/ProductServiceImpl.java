package com.najmi.shop.product.service;

import com.najmi.shop.product.controller.ImageModel;
import com.najmi.shop.product.controller.ProductModel;
import com.najmi.shop.product.orm.Product;
import com.najmi.shop.product.orm.ProductImage;
import com.najmi.shop.product.orm.ProductImageRepository;
import com.najmi.shop.product.orm.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;


    @Override
    public List<ProductModel> list() throws Exception {
        return productRepository
                .findAll()
                .stream()
                .map(ProductServiceImpl::convertToModel)
                .toList();
    }

    @Override
    public ProductModel find(Integer id) throws Exception {
        return convertToModel(productRepository.findById(id).orElseThrow());
    }

    @Override
    public ProductModel create(ProductModel productModel) throws Exception {
        var product = productRepository.save(convertToEntity(productModel));
        for (ImageModel image : productModel.getImages()) {
            productImageRepository.save(new ProductImage(null, product, image.getImg()));
        }
        return convertToModel(product);
    }


    public static Product convertToEntity(ProductModel productModel) {
        var product = new Product();
        BeanUtils.copyProperties(productModel, product);
        return product;
    }

    public static ProductModel convertToModel(Product product) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(product, productModel);
        productModel.setImages(new ArrayList<>());
        if (product.getProductImages() != null) {
            for (ProductImage productImage : product.getProductImages()) {
                productModel.getImages().add(new ImageModel(productImage.getImage()));
            }
        }
        return productModel;
    }
}
