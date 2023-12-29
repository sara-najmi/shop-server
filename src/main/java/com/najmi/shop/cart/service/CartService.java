package com.najmi.shop.cart.service;


import com.najmi.shop.cart.controller.CartModel;

public interface CartService {

    CartModel addToCart(CartModel cartModel);

    CartModel deleteFromCart(CartModel cartModel);
}
