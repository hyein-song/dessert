package com.shopping.dessert.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.shopping.dessert.auth.PrincipalDetails;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
import com.shopping.dessert.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository){
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
        String useremail = "";
        try {
            useremail = JWT
                    .require(Algorithm.HMAC512("SecretKey@@!!"))
                    .build()
                    .verify(jwtToken)
                    .getClaim("userEmail")
                    .asString();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (useremail != null){
            UserEntity user = userRepository.findByEmail(useremail).orElseThrow(()->{
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });
            PrincipalDetails principalDetails = new PrincipalDetails(user);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails, null, principalDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);

    }
}
