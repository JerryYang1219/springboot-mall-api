package com.jerryyang.springbootmall.dao.impl;


import com.jerryyang.springbootmall.dao.UserDao;
import com.jerryyang.springbootmall.dto.UserRegisterRequest;
import com.jerryyang.springbootmall.model.User;
import com.jerryyang.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {
        //定義 SQL 查詢語句，並使用具名參數 :userId
        String sql = "SELECT user_id, email, password, created_date, last_modified_date "+
                "FROM user WHERE user_id = :userId";

        //建立 HashMap 容器，將方法傳入的 userId 放入 map 中，以便 Spring JDBC 映射至 SQL 參數
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        //執行查詢。使用 query 並配合 UserRowMapper
        //將資料庫回傳的每一列 (Row) 轉換成一個 User 物件，最終回傳一個 List 集合
        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        //判斷查詢結果是否為空
        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date "+
                "FROM user WHERE email = :email";

        //建立 HashMap 容器，將方法傳入的 userId 放入 map 中，以便 Spring JDBC 映射至 SQL 參數
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        //執行查詢。使用 query 並配合 UserRowMapper
        //將資料庫回傳的每一列 (Row) 轉換成一個 User 物件，最終回傳一個 List 集合
        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        //判斷查詢結果是否為空
        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        //定義 SQL 語法，使用具名參數確保資料安全性
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :created_date, :last_modified_date)";

        //建立 HashMap 容器，將前端傳入的註冊資訊 (Email, Password) 放至map中之後可映射至 SQL 參數中
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        //取得當前系統時間
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        //宣告 KeyHolder 物件，準備接收資料庫在執行 INSERT 後自動產生的 User ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //執行資料庫更新。透過 MapSqlParameterSource 將 Map 轉為 Spring JDBC 要求的參數格式，並連結 keyHolder
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        //從 keyHolder 中取得資料庫回傳的自動遞增 ID，並轉換為 int 型態
        int userId = keyHolder.getKey().intValue();

        return userId;

    }
}
