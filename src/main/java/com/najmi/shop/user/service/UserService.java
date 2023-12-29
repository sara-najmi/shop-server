package com.najmi.shop.user.service;



import com.najmi.shop.user.controller.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> list() throws Exception;
}
