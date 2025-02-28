package com.hunt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration of Interceptor
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
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
    @Bean
    public WebClient.Builder getWebClientBuilder(){
        return WebClient.builder();
    }
}

