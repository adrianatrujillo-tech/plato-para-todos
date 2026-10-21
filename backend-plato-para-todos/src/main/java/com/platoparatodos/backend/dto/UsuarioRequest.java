package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String nombre,
        @Email @NotBlank String correo,
        @NotBlank String password,
        @NotNull Rol rol,
        String telefono,
        String organizacion,
        String direccion
) {
}
