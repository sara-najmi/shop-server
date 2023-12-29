package com.najmi.shop.user.controller;


import com.najmi.shop.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public record UserController(UserService userService) {


    @GetMapping("")
    public List<UserModel> list() throws Exception {
        return userService.list();
    }

}
