package com.jerryyang.springbootmall.dao;

import com.jerryyang.springbootmall.dto.UserRegisterRequest;
import com.jerryyang.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);


}
