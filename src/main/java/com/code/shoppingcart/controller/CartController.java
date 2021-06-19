package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.CartDto;
import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> create(
            @RequestBody @Valid CartDto dto) {
        return cartService.create(dto);
    }
}
