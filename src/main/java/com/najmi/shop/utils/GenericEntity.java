package com.najmi.shop.utils;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class GenericEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_date")
    private Date updatedAt;

    @Column(name = "invalid")
    private Boolean invalid = Boolean.FALSE;

    @Column(name = "hidden")
    private Boolean hidden = Boolean.FALSE;
}
