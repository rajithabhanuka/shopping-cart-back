package com.code.shoppingcart.service;

import com.code.shoppingcart.dto.ResponseDto;
import org.springframework.http.ResponseEntity;



public interface ProductService {

    ResponseEntity<ResponseDto> getAll(int page, int size);

}
