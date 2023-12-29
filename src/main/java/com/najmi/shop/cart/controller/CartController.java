package com.najmi.shop.cart.controller;


import com.najmi.shop.cart.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public record CartController(CartService cartService) {


    @PostMapping("add-to-cart")
    public CartModel addToCart(@RequestBody CartModel cartModel){
        return cartService.addToCart(cartModel);
    }


    @PostMapping("delete-from-cart")
    public CartModel deleteFromCart(@RequestBody CartModel cartModel){
        return cartService.deleteFromCart(cartModel);
    }
}
