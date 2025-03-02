package com.hunt.config;

import com.hunt.filter.JustAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JustAuthFilter justAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 关闭 CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态 Session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth/render", "/oauth/callback/**","/course/**").permitAll() // 允许的接口
                        .requestMatchers("/oauth/me", "/protected/**").authenticated() // 需要认证的接口
                        .anyRequest().authenticated()
                )
                .addFilterBefore(justAuthFilter, UsernamePasswordAuthenticationFilter.class); // 添加 JustAuthFilter

        return http.build();
    }
}
