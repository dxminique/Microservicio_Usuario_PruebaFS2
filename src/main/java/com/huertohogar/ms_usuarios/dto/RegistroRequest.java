package com.huertohogar.ms_usuarios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
    private String nombre;
    private String email;
    private String password;
}
