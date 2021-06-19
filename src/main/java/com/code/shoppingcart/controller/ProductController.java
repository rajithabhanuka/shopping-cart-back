package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.ProductDto;
import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return productService.getAll(page, size);
    }

}
