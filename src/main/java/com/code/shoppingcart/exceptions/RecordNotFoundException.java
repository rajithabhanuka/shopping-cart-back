package com.code.shoppingcart.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordNotFoundException extends RuntimeException{

    private String message;

    private String variable;
}
