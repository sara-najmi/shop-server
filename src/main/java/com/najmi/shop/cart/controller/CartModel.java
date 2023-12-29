package com.najmi.shop.cart.controller;

import lombok.Data;

@Data
public class CartModel {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer count;
}
