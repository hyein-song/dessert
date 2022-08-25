package com.shopping.dessert.service;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    @Transactional
    public void register(UserDto.Request.RegisterForm registerForm) {

//        validateDuplicatedUser(registerForm);
        UserEntity user = UserDto.Request.RegisterForm.toEntity(registerForm);
        userRepository.save(user);
    }

//    private void validateDuplicatedUser(UserDto.Request.RegisterForm registerForm){
//        Optional<UserEntity> findUser = userRepository.findByEmail(registerForm.getEmail());
//        findUser.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        });
//    }


}
