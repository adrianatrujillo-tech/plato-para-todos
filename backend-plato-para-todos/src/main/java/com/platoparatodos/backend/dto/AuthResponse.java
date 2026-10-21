package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.Rol;

public record AuthResponse(
        Long id,
        String nombre,
        String correo,
        Rol rol,
        String token
) {
}
