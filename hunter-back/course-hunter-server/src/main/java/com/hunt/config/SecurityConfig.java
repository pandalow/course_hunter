package com.hunt.config;

import com.hunt.service.impl.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // 启用 CORS
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/me", "/course/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"status\": \"success\" }");
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"status\": \"logout\" }");
                        })
                );
        return http.build();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许 Cookie 传递
        config.setAllowedOrigins(List.of("http://localhost:5173")); // 允许前端访问
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
