package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String correo,
        Rol rol,
        String telefono,
        String organizacion,
        String direccion,
        boolean activo
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getTelefono(),
                usuario.getOrganizacion(),
                usuario.getDireccion(),
                usuario.isActivo()
        );
    }
}
