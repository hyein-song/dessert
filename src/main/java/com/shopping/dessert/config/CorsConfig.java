package com.shopping.dessert.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// cors 차단을 해제한 클래스
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // json 서버 응답을 자바스크립트에서 처리할 수 있게 해줌
        configuration.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP method에 허용
        configuration.addAllowedHeader("*"); // 모든 HTTP header에 허용
        source.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(source);
    }
}
