package com.huertohogar.ms_usuarios.controller;

import com.huertohogar.ms_usuarios.dto.LoginRequest;
import com.huertohogar.ms_usuarios.dto.LoginResponse;
import com.huertohogar.ms_usuarios.dto.RegistroRequest;
import com.huertohogar.ms_usuarios.model.Usuario;
import com.huertohogar.ms_usuarios.repository.UsuarioRepository;
import com.huertohogar.ms_usuarios.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService authService, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = authService.login(request);

        LoginResponse response = new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getRol()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody RegistroRequest request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setEmail(request.getEmail());
        nuevo.setPassword(request.getPassword()); // texto plano
        nuevo.setRol("USER");
        nuevo.setActivo(true);

        Usuario guardado = usuarioRepository.save(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
