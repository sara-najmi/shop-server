package com.najmi.shop.cart.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Integer> {


    @Query(value = "SELECT * FROM CART WHERE USER_ID = :user_id AND IS_PAYED = :isPayed"
            , nativeQuery = true)
    Cart findByUserIdAndIsPayed(Integer user_id, Boolean isPayed);
}
