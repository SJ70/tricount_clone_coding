package com.example.tricount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)                  // 개발 단계에서의 일시적인 csrf 비활성화

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/member/join").permitAll()    // 특정 경로 요청 시 인증 불필요
                        .anyRequest().authenticated());                 // 나머지 요청 시 인증 필요

        return http.build();
    }

}