package com.jerryyang.springbootmall.rowmapper;

import com.jerryyang.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
  UserRowMapper 負責將資料庫回傳的 ResultSet 轉換為 User 實體物件。
  此類別為 DAO 層專用，確保資料存取邏輯與物件轉換邏輯的分離。
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreateDate(rs.getTimestamp("created_date"));
        user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return user;
    }

}
