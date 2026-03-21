package com.jerryyang.springbootmall.service;

import com.jerryyang.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);


}
