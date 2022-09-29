package com.shopping.dessert.exceptionHandler;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorcode;

    public CustomException(ErrorCode errorcode){
        super(errorcode.getMessage());
        this.errorcode = errorcode;
    }
}
