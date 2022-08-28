package com.shopping.dessert.controller.validation;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserRegisterValidator implements Validator {

    private final UserRepository userRepository;

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

        if(userRepository.existsByEmail(registerForm.getEmail())){
            errors.rejectValue("email","emailDuplicated","이미 존재하는 이메일입니다.");
        }

    }
}
