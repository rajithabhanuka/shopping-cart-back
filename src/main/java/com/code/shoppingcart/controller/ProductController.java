package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.ProductDto;
import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid ProductDto dto) {
        return productService.create(dto);
    }

    /**
     *
     * @param page for pagination
     * @param size for pagination
     * @return list of products in the database with pagination
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return productService.getAll(page, size);
    }

}
