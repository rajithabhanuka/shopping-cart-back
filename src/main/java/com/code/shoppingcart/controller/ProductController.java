package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.ProductDto;
import com.code.shoppingcart.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<ResponseDto> getProducts() {

        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Bhanuka");

        return ResponseEntity.ok(productDto);
    }

}
