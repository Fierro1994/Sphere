package com.example.Sphere.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcCorsConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("http://localhost:3000/**")
                .allowedOrigins("*")
                .allowCredentials(false)
                .maxAge(3600)
                .allowedHeaders("Accept", "Content-Type", "Origin",
                        "Authorization", "Bearer")
                .exposedHeaders("Bearer", "Authorization")
                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
    }
}