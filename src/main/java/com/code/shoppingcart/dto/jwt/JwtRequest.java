package com.code.shoppingcart.dto.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @NotEmpty(message = "Invalid username!")
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    @NotEmpty(message = "Invalid password!")
    private String password;

}
