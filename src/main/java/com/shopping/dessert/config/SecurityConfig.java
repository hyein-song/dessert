package com.shopping.dessert.config;

import com.shopping.dessert.jwt.JwtAuthenticationFilter;
import com.shopping.dessert.jwt.JwtAuthorizationFilter;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final UserRepository userRepository;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/cart/**","/users/**","/order/**").authenticated()
//                    .anyRequest().permitAll()
//                    .and()
//                .formLogin()
//                    .loginPage("/account/login")
//                    .loginProcessingUrl("/account/loginProc")
//                    .permitAll()
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/")
//                    .and()
//                .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
//                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true);

        http.csrf().disable();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .authorizeRequests()
                .antMatchers("/cart/**","/users/**","/order/**").authenticated()
                .anyRequest().permitAll();


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
//        AuthenticationConfiguration configuration = new AuthenticationConfiguration();
        return authenticationConfiguration.getAuthenticationManager();
    }
}
