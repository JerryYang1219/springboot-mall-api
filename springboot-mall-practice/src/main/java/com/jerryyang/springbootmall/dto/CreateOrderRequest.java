package com.jerryyang.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/*
 * 建立訂單時的請求包裝類別 (Request Object)。
 * 負責接收前端傳來的整份購物清單。
 */
public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
