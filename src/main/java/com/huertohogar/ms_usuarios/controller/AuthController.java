package com.huertohogar.ms_usuarios.controller;

import com.huertohogar.ms_usuarios.dto.LoginRequest;
import com.huertohogar.ms_usuarios.dto.LoginResponse;
import com.huertohogar.ms_usuarios.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
