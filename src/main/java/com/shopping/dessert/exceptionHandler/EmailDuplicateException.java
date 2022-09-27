package com.shopping.dessert.exceptionHandler;

import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException{

    private ErrorCode errorcode;

    public EmailDuplicateException(String message, ErrorCode errorcode){
        super(message);
        this.errorcode = errorcode;
    }
}
