package com.code.shoppingcart.dto;

import com.code.shoppingcart.model.CartEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CartDto {

    private int id;

    @JsonProperty(value = "user_id")
    int userId;

    @JsonProperty(value = "product_id")
    private int productId;

    @JsonProperty(value = "qty")
    private int qty;

    @JsonProperty(value = "price")
    private Double price;

    @JsonProperty(value = "order_type")
    private String orderType;

    public CartEntity toEntity() {
        CartEntity userEntity = new CartEntity();
        BeanUtils.copyProperties(this, userEntity);
        return userEntity;
    }
}
