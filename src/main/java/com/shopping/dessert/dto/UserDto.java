package com.shopping.dessert.dto;

import lombok.Builder;
import lombok.Data;


public class UserDto {
    public static class Request{

        @Data
        public static class Register{
            private String email;

            private String name;

            private String password;

            private String phone;

            private String address;

        }
    }

    public static class Response{

    }
}
