package com.shopping.dessert.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(CustomException ex){
//        log.error(ex.getMessage(),ex);
//        ErrorResponse response = new ErrorResponse(ex.getErrorcode());
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorcode().getStatus()));
//    }

    @ExceptionHandler(CustomException.class)
    public String handleEmailDuplicateException(CustomException ex, Model model){
        log.error(ex.getMessage(),ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorcode());

        model.addAttribute("response",response);

        return "error/error";
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorcode().getStatus()));

    }



    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse((ErrorCode.SERVER_ERROR));
        model.addAttribute("response",response);

        return "error/error";
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
