package com.najmi.shop.cart.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    @Query(value = "SELECT * fROM CART_ITEM WHERE CART_ID = :cart_id AND PRODUCT_ID = :product_id",
            nativeQuery = true)
    CartItem findByCartIdAndProductId(Integer cart_id, Integer product_id);

    @Query(value = """
            SELECT CI.* FROM CART_ITEM CI JOIN CART C ON CI.CART_ID = C.ID
            WHERE CI.PRODUCT_ID = :product_id AND C.USER_ID = :cart_user_id
            """ ,nativeQuery = true)
    CartItem findByCartUserIdAndProductId(Integer cart_user_id, Integer product_id);
}
