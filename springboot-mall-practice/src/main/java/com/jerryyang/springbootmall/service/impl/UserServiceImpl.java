package com.jerryyang.springbootmall.service.impl;

import com.jerryyang.springbootmall.dao.UserDao;
import com.jerryyang.springbootmall.dto.UserRegisterRequest;
import com.jerryyang.springbootmall.model.User;
import com.jerryyang.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //創造新帳號前，先檢查此mail有沒有被註冊過
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        //如果有使用者的資料，回傳http狀態碼400 BAD_REQUEST 表示前端請求參數有問題
        if(user != null){
            log.warn("該email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }
}
