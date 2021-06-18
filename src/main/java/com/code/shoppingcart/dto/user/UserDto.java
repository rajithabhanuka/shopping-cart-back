package com.code.shoppingcart.dto.user;


import com.code.shoppingcart.dto.BaseDto;
import com.code.shoppingcart.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseDto {

    @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$", message = "Invalid Email")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @Pattern(regexp = "^([^0-9]*)$", message = "Name must not contain numbers")
    @NotEmpty(message = "Empty values are not allowed!-name")
    private String name;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+"),
            @Pattern(regexp = "(?=.*[a-z]).+"),
            @Pattern(regexp = "(?=.*[A-Z]).+"),
            @Pattern(regexp = "(?=.*[!@#\\$%\\^&\\*]).+"),
            @Pattern(regexp = "(?=\\S+$).+")
    })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("role_id")
    private int roleId;

    @JsonProperty("role_name")
    private String roleName;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(this, userEntity);
        return userEntity;
    }
}
