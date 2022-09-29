package com.shopping.dessert.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON_ERROR_404","PAGE NOT FOUND"),
    SERVER_ERROR(500,"COMMON_ERROR_500","SERVER ERROR"),

    // USER
    EMAIL_DUPLICATION(1000,"USER_ERROR_1000","EMAIL DUPLICATED"),
    PASSWORD_INVALID(1001,"USER_ERROR_1001","PASSWORD INVALID"),
    USER_NOT_FOUND(1002,"USER_ERROR_1002","USER NOT FOUND")
    ;

    private int status;
    private String errorCode;
    private String message;

}
