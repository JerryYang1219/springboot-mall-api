package com.jerryyang.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class User {
    private Integer userId;

    //當 Spring Boot 將這個 User class 去轉換成是一個 json 格式的時候，
    //就會將 email 變數的 key 去轉換成是 e_mail
    @JsonProperty("e_mail")
    private String email;

    //當 Spring Boot 在轉換 User object 為 json 格式的時候就會去忽略這個 password 的變數
    @JsonIgnore
    private String password;

    private Date createDate;
    private Date lastModifiedDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
