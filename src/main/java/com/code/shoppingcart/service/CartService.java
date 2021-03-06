package com.code.shoppingcart.service;

import com.code.shoppingcart.dto.CartDto;
import com.code.shoppingcart.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<ResponseDto> addToCart(CartDto dto);

    ResponseEntity<ResponseDto> getByUserId(int userId);

    ResponseEntity<ResponseDto> deleteCartById(int cartId, int userId);
}
