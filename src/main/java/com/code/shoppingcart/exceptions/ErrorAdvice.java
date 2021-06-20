package com.code.shoppingcart.exceptions;

import com.code.shoppingcart.constants.ErrorCodes;
import com.code.shoppingcart.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateRecordException(DuplicateRecordException ex) {

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(ErrorCodes.X99001)
                .message(ex.getMessage())
                .variable(ex.getVariable()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRecordNotFoundException(RecordNotFoundException ex) {

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(ErrorCodes.X99002)
                .message(ex.getMessage())
                .variable(ex.getVariable()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordNotMatchException(PasswordNotMatchException ex) {

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(ErrorCodes.X99005)
                .message(ex.getMessage())
                .variable(ex.getVariable()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

}
