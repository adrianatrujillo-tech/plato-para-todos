package com.platoparatodos.backend.service;

import com.platoparatodos.backend.model.*;
import com.platoparatodos.backend.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccesoService {
  private final UsuarioRepository usuarios;

  public AccesoService(UsuarioRepository usuarios) {
    this.usuarios = usuarios;
  }

  public Usuario actual() {
    var a = SecurityContextHolder.getContext().getAuthentication();
    if (a == null || !(a.getPrincipal() instanceof Long))
      throw new AccessDeniedException("Sesión requerida");
    return usuarios
        .findById((Long) a.getPrincipal())
        .orElseThrow(() -> new AccessDeniedException("Cuenta no disponible"));
  }

  public void propio(Long id) {
    var u = actual();
    if (u.getRol() != Rol.ADMIN && !u.getId().equals(id))
      throw new AccessDeniedException("Solo puedes acceder a tus registros");
  }

  public void rol(Rol rol) {
    var u = actual();
    if (u.getRol() != Rol.ADMIN && u.getRol() != rol)
      throw new AccessDeniedException("Rol no autorizado");
  }

  public void solicitud(Solicitud s) {
    var u = actual();
    if (u.getRol() != Rol.ADMIN
        && !u.getId().equals(s.getBeneficiario().getId())
        && !u.getId().equals(s.getDonacion().getDonador().getId()))
      throw new AccessDeniedException("Solicitud de otra cuenta");
  }
}
