package com.shopping.dessert.service;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserDto.Request.RegisterForm registerForm) {
//        validateDuplicatedUser(registerForm);
        String originPW = registerForm.getPassword();
        registerForm.setPassword(passwordEncoder.encode(originPW));
        UserEntity user = UserDto.Request.RegisterForm.toEntity(registerForm);
        userRepository.save(user);
    }

//    private void validateDuplicatedUser(UserDto.Request.RegisterForm registerForm){
//        boolean exitsUser =  userRepository.existsByEmail(registerForm.getEmail());
//        if (exitsUser){
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        };
//    }
}
