package com.najmi.shop.user.controller;


import com.najmi.shop.utils.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends GenericModel {

    private Integer id;

    private String firstname;

    private String lastname;

    private String nationalCode;

    private String uniId;
}
