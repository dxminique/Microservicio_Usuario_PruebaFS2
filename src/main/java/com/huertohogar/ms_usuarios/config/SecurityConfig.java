package com.huertohogar.ms_usuarios.config;

import com.huertohogar.ms_usuarios.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/usuarios/ping").permitAll()



                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**")
                        .hasAnyRole("USER", "ADMIN")

                        // crear / actualizar / eliminar → solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**")
                        .hasRole("ADMIN")

                        // cualquier otra ruta → autenticada
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
