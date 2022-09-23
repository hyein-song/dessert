package com.shopping.dessert.service;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.UserRepository;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void register() {

        UserDto.Request.RegisterForm registerForm = UserDto.Request.RegisterForm
                .builder()
                .name("test")
                .email("test@naver.com")
                .password("Qwerty123!")
                .passwordConfirm("Qwerty123!")
                .address("주소")
                .phone("1")
                .build();

        boolean exitsUser = false;

        UserEntity user = UserDto.Request.RegisterForm.toEntity(registerForm);

        doReturn(exitsUser).when(userRepository).existsByEmail(registerForm.getEmail());
        doReturn(user).when(userRepository).save(any(UserEntity.class));

        userService.register(registerForm);
//        assertEquals(user.getEmail(), userService. );
//        verify(userService).register(registerForm);


    }

    @Test
    void updateMyInfo() {
    }

    @Test
    void delete() {
    }
}