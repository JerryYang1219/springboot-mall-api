package com.jerryyang.springbootmall.dao.impl;


import com.jerryyang.springbootmall.dao.OrderDao;
import com.jerryyang.springbootmall.model.OrderItem;
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
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        //新增sql之指令，`order`避免語法錯誤
        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:user_id, :total_amount, :created_date, :last_modified_date)";

        //建立參數容器，將 Service 層傳來的 userId (誰買的) 與 totalAmount (總共多少錢) 放入
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("total_amount", totalAmount);

        //取得當前系統時間，同時賦予建立與修改時間
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        //宣告 KeyHolder，準備接收由資料庫自動生成的 order_id
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //將參數 Map 轉為 MapSqlParameterSource 並傳入 keyHolder
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        //抓取新 ID
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        //使用 batchUpdate 一次性加入數據，效率更高
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:order_id, :product_id, :quantity, :amount)";

        //準備一個「參數陣列」，長度等於訂單明細的數量。
        MapSqlParameterSource [] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        //使用 for 迴圈將每一項商品資料，封裝進對應的參數物件中
        for ( int i = 0; i < orderItemList.size(); i++){
            OrderItem orderItem = orderItemList.get(i);

            //初始化陣列中的每一個元素
            parameterSources[i] = new MapSqlParameterSource();
            //將資料逐一放入，注意 order_id 是從 Service 層傳下來的「同一張訂單編號」
            parameterSources[i].addValue("order_id", orderId);
            parameterSources[i].addValue("product_id", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        //執行批次更新。Spring 會將這一組參數陣列一次性發送給資料庫，大幅減少連線開銷
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
