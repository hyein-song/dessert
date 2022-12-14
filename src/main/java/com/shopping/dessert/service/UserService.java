package com.shopping.dessert.service;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(UserDto.Request.RegisterForm registerForm){
        boolean exitsUser =  userRepository.existsByEmail(registerForm.getEmail());
        if (exitsUser){
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        };

        String originPW = registerForm.getPassword();
        registerForm.setPassword(passwordEncoder.encode(originPW));
        UserEntity user = UserDto.Request.RegisterForm.toEntity(registerForm);
        userRepository.save(user);
    }

    @Transactional
    public void updateMyInfo(UserDto.Request.MyInfoUpdateForm updateForm){
        UserEntity updatedUser = userRepository.findByEmail(updateForm.getEmail()).orElseThrow(()->{
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        String originPW = updateForm.getPassword();
        updateForm.setPassword(passwordEncoder.encode(originPW));

        updatedUser.changeUserInfo(updateForm);

        // 세션 업데이트
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updatedUser.getEmail(),originPW));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Transactional
    public void delete(UserDto.Request.UserDeleteForm userDeleteForm){
        UserEntity user = userRepository.findByEmail(userDeleteForm.getEmail()).orElseThrow(()->{
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        String originPW = userDeleteForm.getPassword();
        if (passwordEncoder.matches(originPW, userDeleteForm.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }

        userRepository.delete(user);

    }

}
