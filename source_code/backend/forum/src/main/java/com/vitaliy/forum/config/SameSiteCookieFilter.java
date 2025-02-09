// package com.vitaliy.forum.config;

// import java.io.IOException;

// import org.springframework.stereotype.Component;

// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class SameSiteCookieFilter implements Filter {
//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//             throws IOException, ServletException {
//         if (response instanceof HttpServletResponse) {
//             HttpServletResponse res = (HttpServletResponse) response;
//             // Thêm Secure và SameSite=None vào cookie
//             res.setHeader("Set-Cookie", "JSESSIONID=" + res.encodeURL("JSESSIONID") + "; Path=/; HttpOnly; Secure; SameSite=None");
//         }
//         chain.doFilter(request, response);
//     }
// }
