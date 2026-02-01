package com.hunt.filter;

import com.hunt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

/**
 * 
 */
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    final private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);

        try{
            Claims claims = jwtUtils.extractAllClaims(jwt);
            String googleId = claims.getSubject();
            String role = (String)claims.get("role");
            if(googleId != null && SecurityContextHolder.getContext().getAuthentication() == null){

                // Create a verification credential
                // 参数1：Principal（当事人，通常是 email 或 User 对象）
                // 参数2：Credentials（凭证，JWT 场景下传 null 即可）
                // 参数3：Authorities（权限列表，可以从 Claims 里的 Role 转换而来）
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        googleId,
                        null,
                        authorities
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            logger.warn("JWT validation failed: " + e.getMessage());
        }

        // 继续执行请求
        filterChain.doFilter(request, response);
    }
}
