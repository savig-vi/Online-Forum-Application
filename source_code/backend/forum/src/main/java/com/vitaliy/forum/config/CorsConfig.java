// package com.vitaliy.forum.config;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig implements WebMvcConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("http://127.0.0.1:5500") // Domain của frontend
//                 .allowedMethods("GET", "POST", "PUT", "DELETE")
//                 .allowCredentials(true); // Cho phép gửi cookie
//     }

//     // @Bean
//     // public CorsFilter corsFilter() {
//     //     CorsConfiguration config = new CorsConfiguration();
//     //     // Cho phép yêu cầu từ địa chỉ frontend
//     //     config.addAllowedOrigin("http://127.0.0.1:5500"); // Địa chỉ frontend của bạn
//     //     config.addAllowedMethod("GET");
//     //     config.addAllowedMethod("POST");
//     //     config.addAllowedMethod("PUT");
//     //     config.addAllowedMethod("DELETE");
//     //     config.addAllowedMethod("OPTIONS");
//     //     config.addAllowedHeader("*");  // Cho phép tất cả headers
//     //     config.setAllowCredentials(true);  // Cho phép gửi cookies qua cors
//     //     config.setExposedHeaders(List.of("Set-Cookie"));
//     //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     //     source.registerCorsConfiguration("/**", config);  // Áp dụng cho tất cả API
//     //     return new CorsFilter(source);
//     // }
// }
