package com.platoparatodos.backend.service;

import com.platoparatodos.backend.dto.AuthResponse;
import com.platoparatodos.backend.dto.LoginRequest;
import com.platoparatodos.backend.dto.UsuarioRequest;
import com.platoparatodos.backend.dto.UsuarioResponse;
import com.platoparatodos.backend.exception.OperacionInvalidaException;
import com.platoparatodos.backend.model.Usuario;
import com.platoparatodos.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
                .orElseThrow(() -> new OperacionInvalidaException("Correo o contrasena incorrectos"));

        if (!usuario.getPassword().equals(request.password())) {
            throw new OperacionInvalidaException("Correo o contrasena incorrectos");
        }

        if (!usuario.isActivo()) {
            throw new OperacionInvalidaException("Usuario inactivo");
        }

        return new AuthResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                "demo-" + UUID.randomUUID()
        );
    }

    public UsuarioResponse registrar(UsuarioRequest request) {
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new OperacionInvalidaException("El correo ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setCorreo(request.correo());
        usuario.setPassword(request.password());
        usuario.setRol(request.rol());
        usuario.setTelefono(request.telefono());
        usuario.setOrganizacion(request.organizacion());
        usuario.setDireccion(request.direccion());

        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }
}
