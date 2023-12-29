package com.najmi.shop.user.service;


import com.najmi.shop.user.controller.UserModel;
import com.najmi.shop.user.orm.User;
import com.najmi.shop.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserModel> list() throws Exception {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public UserModel convertToDto(User user) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }
}
