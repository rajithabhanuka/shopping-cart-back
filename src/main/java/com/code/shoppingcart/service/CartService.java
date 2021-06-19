package com.code.shoppingcart.service;

import com.code.shoppingcart.dto.CartDto;
import com.code.shoppingcart.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<ResponseDto> create(CartDto dto);

    ResponseEntity<ResponseDto> getByUserId(int userId);
}
