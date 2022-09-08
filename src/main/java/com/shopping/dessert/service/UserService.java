package com.shopping.dessert.service;

import com.shopping.dessert.auth.PrincipalDetails;
import com.shopping.dessert.auth.PrincipalDetailsService;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        };

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
    public void updateMyInfo(UserDto.Request.MyInfoUpdateForm updateForm){
        UserEntity updatedUser = userRepository.findByEmail(updateForm.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        String originPW = updateForm.getPassword();
        updateForm.setPassword(passwordEncoder.encode(originPW));

        updatedUser.changeUserInfo(updateForm);

        // 세션 업데이트
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updatedUser.getEmail(),originPW));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Transactional
    public void delete(UserDto.Request.UserDeleteForm userDeleteForm, UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(userDeleteForm.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        if (!user.getEmail().equals(currentUser.getEmail())){
            throw new IllegalStateException("로그인된 사용자가 아닙니다.");
        }

        String originPW = userDeleteForm.getPassword();
        if (passwordEncoder.matches(originPW, userDeleteForm.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);

    }

}
