package com.company.ticket_booking_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/user/register",
                                "/api/user/**",
                                "/actuator/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Disable default login form
                .formLogin(form -> form.disable())

                // Disable basic auth popup
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
