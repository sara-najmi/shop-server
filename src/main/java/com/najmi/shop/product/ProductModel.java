package com.najmi.shop.product;

import com.najmi.shop.utils.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductModel extends GenericModel {

    private Long id;

    private String title;

    private BigDecimal price;
}
