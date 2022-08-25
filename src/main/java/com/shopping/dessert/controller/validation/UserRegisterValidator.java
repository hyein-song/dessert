package com.shopping.dessert.controller.validation;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserRegisterValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.Request.RegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto.Request.RegisterForm registerForm = (UserDto.Request.RegisterForm)target;

        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirm())){
            errors.rejectValue("passwordConfirm","passwordIncorrect", "비밀번호가 일치하지 않습니다.");
        }

//        try {
//            userService.register(registerForm);
//        } catch (DataIntegrityViolationException e){
//            e.printStackTrace();
//            errors.reject("registerFailed","이미 등록된 사용자 입니다.");
//        } catch (Exception e){
//            e.printStackTrace();
//            errors.reject("registerFailed",e.getMessage());
//        }

    }
}
