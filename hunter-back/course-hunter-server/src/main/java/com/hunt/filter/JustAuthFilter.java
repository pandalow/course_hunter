package com.hunt.filter;

import com.hunt.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JustAuthFilter extends OncePerRequestFilter {

    private final UserService userService; // 需要用来查询用户

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取前端请求的 Token
        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // 去掉 `Bearer ` 前缀

            // 从 Redis 查询是否存在该 Token
            Optional<String> googleIdOpt = userService.getGoogleIdByAccessToken(accessToken);

            if (googleIdOpt.isPresent()) {
                // 获取用户信息
                UserDetails userDetails = User.builder()
                        .username(googleIdOpt.get())
                        .password("") // Google 登录不需要密码
                        .roles("User") // 你可以扩展用户角色
                        .build();

                // 设置 Spring Security 认证信息
                var authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 存入 SecurityContext，供后续接口使用
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 继续执行请求
        filterChain.doFilter(request, response);
    }
}
