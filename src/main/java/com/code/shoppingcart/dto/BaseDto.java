package com.code.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseDto implements ResponseDto{

    private int id;

    @JsonProperty("is_active")
    private boolean isActive = true;
}
