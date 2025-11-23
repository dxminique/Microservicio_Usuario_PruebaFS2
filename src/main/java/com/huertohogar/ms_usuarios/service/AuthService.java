package com.huertohogar.ms_usuarios.service;

import com.huertohogar.ms_usuarios.dto.LoginRequest;
import com.huertohogar.ms_usuarios.model.Usuario;
import com.huertohogar.ms_usuarios.repository.UsuarioRepository;
import com.huertohogar.ms_usuarios.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Para la evaluación: compara en texto plano
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}
