package com.platoparatodos.backend.controller;

import com.platoparatodos.backend.dto.UsuarioResponse;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> listar(@RequestParam(required = false) Rol rol) {
        if (rol != null) {
            return usuarioService.listarPorRol(rol);
        }

        return usuarioService.listarTodos();
    }
}
