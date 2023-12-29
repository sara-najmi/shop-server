package com.najmi.shop.product.controller;

import com.najmi.shop.utils.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductModel extends GenericModel {

    private Integer id;

    private String title;

    private BigDecimal price;

    private List<ImageModel> images;
}
