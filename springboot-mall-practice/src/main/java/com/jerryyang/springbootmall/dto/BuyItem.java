package com.jerryyang.springbootmall.dto;

import jakarta.validation.constraints.NotNull;

/*
 * 代表訂單中的單一購買項目。
 * 對應 JSON 陣列中的每一個物件元素。
 */

public class BuyItem {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
