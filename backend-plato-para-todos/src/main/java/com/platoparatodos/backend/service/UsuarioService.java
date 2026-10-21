package com.platoparatodos.backend.service;

import com.platoparatodos.backend.dto.UsuarioResponse;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    public List<UsuarioResponse> listarPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(UsuarioResponse::from)
                .toList();
    }
}
