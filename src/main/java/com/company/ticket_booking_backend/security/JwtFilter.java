package com.company.ticket_booking_backend.security;

import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getUserEmailFromToken(token);

                    // Optionally, load user from DB
                    User user = userService.getUserByEmail(email);

                    // Set user info as request attributes
                    request.setAttribute("email", user.getEmail());
                    request.setAttribute("role", user.getRole().name());
                    request.setAttribute("userId", user.getId());
                }
            }
        } catch (Exception e) {
            // Optional: log invalid token attempts
            System.out.println("Invalid JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
