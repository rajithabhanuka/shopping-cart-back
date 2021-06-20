package com.code.shoppingcart.dto.jwt;

import com.code.shoppingcart.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @Getter
    private final String jwtToken;

    @Getter
    @JsonProperty(value = "user")
    private final UserDto userDto;
}
