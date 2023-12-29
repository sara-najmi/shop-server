package com.najmi.shop.product.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT COUNT(ID) FROM PRODUCT", nativeQuery = true)
    Integer countAll();
}
