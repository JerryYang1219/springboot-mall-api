package com.jerryyang.springbootmall.model;

import java.util.Date;

public class Order {
    private Integer orderId;
    private Integer userIf;
    private Integer totalAmout;
    private Date createdDate;
    private Date lastModifiedDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserIf() {
        return userIf;
    }

    public void setUserIf(Integer userIf) {
        this.userIf = userIf;
    }

    public Integer getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(Integer totalAmout) {
        this.totalAmout = totalAmout;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
