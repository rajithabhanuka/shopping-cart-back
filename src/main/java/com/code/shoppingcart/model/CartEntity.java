package com.code.shoppingcart.model;

import com.code.shoppingcart.dto.CartDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity(name = "cart")
@Table(indexes = {@Index(name = "uniqueUserProduct", columnList = "user_id, product_id", unique = true)})
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    int userId;

    @Column(name = "product_id")
    private int productId;

    private int qty;

    private Double price;

    public CartDto toDto(){
        CartDto dto = new CartDto();
        BeanUtils.copyProperties(this,dto);
        return dto;
    }
}
