package com.najmi.shop.invoice;


import com.najmi.shop.cart.controller.CartModel;
import com.najmi.shop.user.controller.UserModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceModel {

    private Integer id;

    private UserModel user;

    private CartModel cart;

    private BigDecimal total;

    private Date createAt;

    private Boolean isDelivered;
}
