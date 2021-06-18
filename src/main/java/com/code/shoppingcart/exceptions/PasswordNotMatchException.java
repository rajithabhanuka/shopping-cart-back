package com.code.shoppingcart.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PasswordNotMatchException extends RuntimeException {

    private final String message;

    private final String variable;

}
