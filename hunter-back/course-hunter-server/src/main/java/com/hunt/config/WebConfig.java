package com.hunt.config;

import com.hunt.constant.InterceptorExcludePathConstant;
import com.hunt.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of Interceptor
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor interceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(InterceptorExcludePathConstant.getExcludePaths());
    }

    /**
     * Add resource handler for Swagger
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler ("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui");
    }

    /**
     * Adding Cors request
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173/") // Allow all domains
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow request
                .allowedHeaders("*") // all headers
                .allowCredentials(true) // all for the cookies
                .maxAge(3600);
    }
}

