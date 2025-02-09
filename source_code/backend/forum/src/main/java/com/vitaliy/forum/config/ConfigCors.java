package com.vitaliy.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Cái này làm cho gửi session thành công nè
@Configuration
@EnableWebMvc
public class ConfigCors implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://127.0.0.1:5501") // Chỉ cho phép frontend chính thức
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // Cho phép gửi cookie
    }
}