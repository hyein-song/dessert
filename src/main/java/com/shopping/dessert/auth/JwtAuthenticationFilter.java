package com.shopping.dessert.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 1. username, password 받음
            UserDto.Request.LoginForm login = objectMapper.readValue(request.getInputStream(), UserDto.Request.LoginForm.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());


            //2. 정상적인 로그인 시도 여부 검증
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // 3. 로그인이 되었다
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            return authentication;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request,response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String jwt = JWT.create()
                .withSubject("JWT_토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + 6000 * 10))
                .withClaim("useremail",principalDetails.getUserEntity().getEmail())
                .sign(Algorithm.HMAC512("SecretKey@@!!"));

        response.addHeader("Authorization","Bearer"+jwt);

//        super.successfulAuthentication(request,response,chain,authResult);
    }
}
