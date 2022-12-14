package com.shopping.dessert.dto;

import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class UserDto {

    public static class Request{

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class RegisterForm{

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
                        .password(register.getPassword())
                        .phone(register.getPhone())
                        .address(register.getAddress())
                        .role(UserRole.ROLE_ADMIN)
                        .build();
            }


        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LoginForm{

            @NotBlank(message = "이메일은 필수 입력 값입니다.")
            private String email;

            @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
            private String password;

        }


        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MyInfoUpdateForm{

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

            public static UserDto.Request.MyInfoUpdateForm of(UserEntity userEntity){
                return UserDto.Request.MyInfoUpdateForm.builder()
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .phone(userEntity.getPhone())
                        .address(userEntity.getAddress())
                        .build();
            }

        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserDeleteForm {

            @NotBlank(message = "이메일은 필수 입력 값입니다.")
            private String email;

            @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
            private String password;


        }

    }

    public static class Response{

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserDetailForOrder{

            private String email;

            private String name;

            private String phone;

            private String address;

            private Long point;

            public static UserDetailForOrder of(UserEntity userEntity){
                return builder()
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .phone(userEntity.getPhone())
                        .address(userEntity.getAddress())
                        .point(userEntity.getPoint())
                        .build();
            }

        }

    }
}
