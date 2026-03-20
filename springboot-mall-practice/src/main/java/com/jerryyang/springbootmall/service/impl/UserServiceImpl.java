package com.jerryyang.springbootmall.service.impl;

import com.jerryyang.springbootmall.dao.UserDao;
import com.jerryyang.springbootmall.dto.UserRegisterRequest;
import com.jerryyang.springbootmall.model.User;
import com.jerryyang.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
