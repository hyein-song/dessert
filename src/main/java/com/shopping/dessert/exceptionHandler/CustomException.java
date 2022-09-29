package com.shopping.dessert.exceptionHandler;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorcode;

    public CustomException(String message, ErrorCode errorcode){
        super(message);
        this.errorcode = errorcode;
    }
}
