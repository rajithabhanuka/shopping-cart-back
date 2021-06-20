package com.code.shoppingcart.controller;


import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.dto.user.UserDto;
import com.code.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param userDto user object to be saved
     * @return response entity with saved user details
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }
}
