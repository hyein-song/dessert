package com.shopping.dessert.service;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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
        validateDuplicatedUser(registerForm);
        String originPW = registerForm.getPassword();
        registerForm.setPassword(passwordEncoder.encode(originPW));
        UserEntity user = UserDto.Request.RegisterForm.toEntity(registerForm);
        userRepository.save(user);
    }

    @Transactional
    public UserDto.Request.MyInfoUpdateForm getMyInfo(UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        return UserDto.Request.MyInfoUpdateForm.of(user);

    }

    @Transactional
    public void updateMyInfo(UserDto.Request.MyInfoUpdateForm updateForm, UserEntity currentUser){
        UserEntity updatedUser = userRepository.findByEmail(updateForm.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        if (!updatedUser.getEmail().equals(currentUser.getEmail())){
            throw new IllegalStateException("로그인된 사용자가 아닙니다.");
        }
        String originPW = updateForm.getPassword();
        updateForm.setPassword(passwordEncoder.encode(originPW));

        updatedUser.changeUserInfo(updateForm);

    }

    @Transactional
    public void delete(UserDto.Request.DeleteForm deleteForm, UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(deleteForm.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        if (!user.getEmail().equals(currentUser.getEmail())){
            throw new IllegalStateException("로그인된 사용자가 아닙니다.");
        }

        String originPW = deleteForm.getPassword();
        if (passwordEncoder.matches(originPW, deleteForm.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);

    }




    private void validateDuplicatedUser(UserDto.Request.RegisterForm registerForm){
        boolean exitsUser =  userRepository.existsByEmail(registerForm.getEmail());
        if (exitsUser){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        };
    }
}
