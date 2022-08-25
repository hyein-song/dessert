package com.shopping.dessert.dto;

import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


public class UserDto {

    public static class Request{

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RegisterForm{

//            final private BCryptPasswordEncoder bCryptPasswordEncoder;

            @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
            @NotBlank(message = "이메일은 필수 입력 값입니다.")
            private String email;

            @NotBlank(message = "이름은 필수 입력 값입니다.")
            private String name;

            @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
            @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
            private String password;

            @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
            private String passwordConfirm;

            private String phone;

            private String address;

            public static UserEntity toEntity(RegisterForm register){
                return UserEntity.builder()
                        .email(register.getEmail())
                        .name(register.getName())
//                        .password(bCryptPasswordEncoder.encode(register.getPassword()))
                        .password(register.getPassword())
                        .phone(register.getPhone())
                        .address(register.getAddress())
                        .joinDateTime(LocalDateTime.now())
                        .role(UserRole.USER_NORMAL)
                        .build();
            }


        }
    }

    public static class Response{

    }
}