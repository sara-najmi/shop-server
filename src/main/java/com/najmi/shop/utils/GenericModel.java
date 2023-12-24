package com.najmi.shop.utils;

import lombok.Data;

import java.util.Date;

@Data
public class GenericModel {

    private Date createdAt;

    private Date updatedAt;

    private Boolean invalid;

    private Boolean hidden;
}
