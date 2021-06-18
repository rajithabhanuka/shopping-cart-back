package com.code.shoppingcart.dto;

import lombok.Data;

@Data
public class ProductDto implements ResponseDto{

    private int id;

    private String name;

}
