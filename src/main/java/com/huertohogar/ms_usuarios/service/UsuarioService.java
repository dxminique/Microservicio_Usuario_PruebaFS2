package com.huertohogar.ms_usuarios.service;

import com.huertohogar.ms_usuarios.model.Usuario;
import com.huertohogar.ms_usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario datos) {
        Usuario existente = getById(id);

        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setPassword(datos.getPassword());
        existente.setRol(datos.getRol());
        existente.setActivo(datos.isActivo());
        return usuarioRepository.save(existente);
    }


    public void delete(Long id) {
        Usuario existente = getById(id);
        usuarioRepository.delete(existente);
    }

}
