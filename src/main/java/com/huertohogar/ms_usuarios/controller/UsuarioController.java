package com.huertohogar.ms_usuarios.controller;

import com.huertohogar.ms_usuarios.model.Usuario;
import com.huertohogar.ms_usuarios.service.UsuarioService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @Operation(
            summary = "Ping de prueba",
            description = "Endpoint para comprobar que el microservicio está funcionando."
    )
    @ApiResponse(responseCode = "200", description = "Microservicio funcionando correctamente")
    @GetMapping("/ping")
    public String ping() {
        return "ms-usuarios OK";
    }


    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve una lista con todos los usuarios registrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.getAll());
    }


    @Operation(
            summary = "Obtener usuario por ID",
            description = "Devuelve un usuario específico según su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(
            @Parameter(description = "ID del usuario a buscar")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    // ------------------------------------------------------------------------------------
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados")
    })
    @PostMapping
    public ResponseEntity<Usuario> crear(
            @Valid @RequestBody Usuario usuario
    ) {
        Usuario creado = usuarioService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @Operation(
            summary = "Actualizar un usuario",
            description = "Modifica los datos de un usuario existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @Parameter(description = "ID del usuario a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario
    ) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }


    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina un usuario mediante su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a eliminar")
            @PathVariable Long id
    ) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
