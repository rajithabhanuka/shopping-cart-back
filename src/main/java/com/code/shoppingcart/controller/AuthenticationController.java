package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.jwt.JwtRequest;
import com.code.shoppingcart.dto.jwt.JwtResponse;
import com.code.shoppingcart.exceptions.PasswordNotMatchException;
import com.code.shoppingcart.model.UserEntity;
import com.code.shoppingcart.security.JwtTokenUtil;
import com.code.shoppingcart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }catch (Exception e){
            throw new PasswordNotMatchException("INVALID_CREDENTIALS", e.getMessage());
        }

        final String token = jwtTokenUtil
                .generateToken(userService.loadUserByUsername(request.getUsername()));

        UserEntity userEntity = userService.getUserDetailsByEmail(request.getUsername());

        return ResponseEntity.ok(new JwtResponse(token, userEntity.toDto()));
    }
}
