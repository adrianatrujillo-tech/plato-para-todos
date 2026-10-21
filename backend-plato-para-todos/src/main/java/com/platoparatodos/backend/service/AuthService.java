package com.platoparatodos.backend.service;

import com.platoparatodos.backend.dto.AuthResponse;
import com.platoparatodos.backend.dto.LoginRequest;
import com.platoparatodos.backend.dto.UsuarioRequest;
import com.platoparatodos.backend.dto.UsuarioResponse;
import com.platoparatodos.backend.exception.OperacionInvalidaException;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.model.Usuario;
import com.platoparatodos.backend.repository.UsuarioRepository;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwords;
  private final TokenService tokens;

  public AuthService(
      UsuarioRepository usuarioRepository, PasswordEncoder passwords, TokenService tokens) {
    this.passwords = passwords;
    this.tokens = tokens;
    this.usuarioRepository = usuarioRepository;
  }

  public AuthResponse login(LoginRequest request) {
    Usuario usuario =
        usuarioRepository
            .findByCorreo(request.correo().trim().toLowerCase(Locale.ROOT))
            .orElseThrow(() -> new OperacionInvalidaException("Correo o contrasena incorrectos"));

    if (!passwords.matches(request.password(), usuario.getPassword())) {
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
        tokens.crear(usuario.getId()));
  }

  public UsuarioResponse registrar(UsuarioRequest request) {
    if (request.rol() == Rol.ADMIN)
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "No se permite crear administradores desde el registro público.");
    if (request.password().length() < 8
        || request.password().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72)
      throw new OperacionInvalidaException(
          "Usa una contraseña de al menos 8 caracteres y hasta 72 bytes.");
    if (usuarioRepository.existsByCorreo(request.correo().trim().toLowerCase(Locale.ROOT))) {
      throw new OperacionInvalidaException("El correo ya esta registrado");
    }

    Usuario usuario = new Usuario();
    usuario.setNombre(request.nombre());
    usuario.setCorreo(request.correo().trim().toLowerCase(Locale.ROOT));
    usuario.setPassword(passwords.encode(request.password()));
    usuario.setRol(request.rol());
    usuario.setTelefono(request.telefono());
    usuario.setOrganizacion(request.organizacion());
    usuario.setDireccion(request.direccion());

    return UsuarioResponse.from(usuarioRepository.save(usuario));
  }
}
