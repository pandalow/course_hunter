package com.hunt.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.constant.JwtConstant;
import com.hunt.dto.UserTokenDTO;
import com.hunt.result.Result;
import com.hunt.utils.JwtUtil;
import com.hunt.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    //Before running
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //get request header
        String token = request.getHeader("token");
        response.setContentType("application/json;charset = UTF-8");
        String userIdStr;
        String email;
        //check if token exists
        if (token == null || token.isEmpty()){
            return writeErrorResponse(response, ExceptionMessageConstant.NOT_AUTHORIZED);
        }
        //parse token to see if it's illegal
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userIdStr = claims.get(JwtConstant.USER_INFO_ID, String.class);
            email = claims.get(JwtConstant.USER_INFO_EMAIL, String.class);

            //check if token match redis
            String tokenInRedis = RedisUtil.getFromRedis(userIdStr, String.class);
            if (!token.equals(tokenInRedis)){
                log.error("Token not match redis");
                return writeErrorResponse(response, ExceptionMessageConstant.NOT_AUTHORIZED);
            }

            //check if token is expired
            Date claimsExpiration = claims.getExpiration();
            if (claimsExpiration.before(new Date())){
                return writeErrorResponse(response, ExceptionMessageConstant.TOKEN_EXPIRED);
            }
        }  catch (RuntimeException e)  {
            log.error(e.getMessage());
            return writeErrorResponse(response, ExceptionMessageConstant.NOT_AUTHORIZED);
        }

        // postpone the expiration time of the token
        RedisUtil.extendExpirationTime(userIdStr, JwtConstant.TOKEN_DURATION);

        // save user info to security context for later use
        // TODO: just some requests needs this, it's better to use aspect
        UserTokenDTO userDetails = new UserTokenDTO(Long.parseLong(userIdStr), email);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return true;
    }

    //After Running
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //After Rendering, running finally
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private static boolean writeErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Result<String> error = Result.error(errorMessage);
        String result = mapper.writeValueAsString(error);
        response.getWriter().write(result);
        return false;
    }
}
