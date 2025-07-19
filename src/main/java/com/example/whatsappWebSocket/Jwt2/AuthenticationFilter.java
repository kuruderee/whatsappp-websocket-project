package com.example.whatsappWebSocket.Jwt2;


import com.example.whatsappWebSocket.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
//@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserService userService;
   // @Override

//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (JwtUtil.isTokenExpired(token)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Token expired");
//            } else {
//                String username = JwtUtil.extractUsername(token);
//                if (username != null) {
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    try {
//                        filterChain.doFilter(request, response);
//                    } catch (Exception e) {
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        response.getWriter().write("Authentication failed: " + e.getMessage());
//                    }
//                }else {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.getWriter().write("Invalid token");
//                }
//            }
//        } else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Authorization header is missing or invalid");
//
//        }
//    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String requestPath = request.getRequestURI(); // Doğru şekilde URI'yi alıyoruz.

        // Yetkilendirme gerekmeyen uç noktalar
        if (requestPath.equals("/login") || requestPath.equals("/login.html") || requestPath.equals("/register.html")||requestPath.equals("/login.css")) {
            filterChain.doFilter(request, response); // Filtreleme olmadan devam et
            return;
        }

        if (authHeader.startsWith("Bearer ")&& authHeader != null) {
            String token = authHeader.substring(7);
            if (JwtUtil.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
            } else {
                String username = JwtUtil.extractUsername(token);
                if (username != null) {
                    filterChain.doFilter(request, response); // Doğrulama başarılı, isteği ilet
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header is missing or invalid");
        }
    }



}




