package com.code.shoppingcart.service;


import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.dto.user.UserDto;
import com.code.shoppingcart.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService extends UserDetailsService {

    ResponseEntity<ResponseDto> create(UserDto createUserRequestModel);

    UserEntity getUserDetailsByEmail(String email);

}
