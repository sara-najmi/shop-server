package com.najmi.shop.product;

import com.najmi.shop.utils.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "price")
    private BigDecimal price;
}


