package com.najmi.shop.invoice;


import com.najmi.shop.cart.orm.CartItem;
import com.najmi.shop.product.orm.Product;
import com.najmi.shop.utils.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "invoice_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CartItem cartItem;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "count")
    private Integer count;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;
}
