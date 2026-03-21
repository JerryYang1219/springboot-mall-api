package com.jerryyang.springbootmall.dao;

import com.jerryyang.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer user, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
