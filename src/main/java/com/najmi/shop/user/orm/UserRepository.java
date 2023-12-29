package com.najmi.shop.user.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNationalCodeOrUniId(String nationalCode, String uniId);

    @Query(value = "SELECT COUNT(ID) FROM USER_", nativeQuery = true)
    Integer countAll();
}
