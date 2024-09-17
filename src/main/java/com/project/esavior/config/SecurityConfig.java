package com.project.esavior.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // Cho phép truy cập không cần xác thực đến H2 Console
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll() // Cho phép truy cập không cần xác thực đến các endpoint xác thực
                        .anyRequest().authenticated() // Mọi yêu cầu khác cần phải được xác thực
                )
                // Cập nhật cấu hình headers cho phép H2 Console hoạt động
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Cho phép sử dụng iframe với cùng nguồn gốc (same origin)
                )
                .httpBasic(withDefaults()); // Sử dụng xác thực HTTP Basic

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
