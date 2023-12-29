package com.najmi.shop.product.orm;

import com.najmi.shop.utils.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }
}


