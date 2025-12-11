package com.huertohogar.ms_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        // Login / registro, etc.
                        .requestMatchers("/auth/**").permitAll()

                        // Swagger y OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()


                        .requestMatchers(HttpMethod.GET,    "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
