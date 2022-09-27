package com.shopping.dessert.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON_ERROR_404","PAGE NOR FOUND"),
    SERVER_ERROR(500,"COMMON_ERROR_500","SERVER ERROR"),
    EMAIL_DUPLICATION(400,"USER_ERROR_400","EMAIL DUPLICATED"),
    ;

    private int status;
    private String errorCode;
    private String message;

}
