package com.platoparatodos.backend.controller;

import com.platoparatodos.backend.dto.DonacionRequest;
import com.platoparatodos.backend.dto.DonacionResponse;
import com.platoparatodos.backend.dto.DonacionUpdateRequest;
import com.platoparatodos.backend.service.DonacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    private final DonacionService donacionService;

    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @GetMapping
    public List<DonacionResponse> listar(
            @RequestParam(defaultValue = "false") boolean disponibles,
            @RequestParam(required = false) Long donadorId
    ) {
        if (donadorId != null) {
            return donacionService.listarPorDonador(donadorId);
        }

        if (disponibles) {
            return donacionService.listarDisponibles();
        }

        return donacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public DonacionResponse obtener(@PathVariable Long id) {
        return donacionService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonacionResponse crear(@Valid @RequestBody DonacionRequest request) {
        return donacionService.crear(request);
    }

    @PutMapping("/{id}")
    public DonacionResponse editar(@PathVariable Long id, @Valid @RequestBody DonacionUpdateRequest request) {
        return donacionService.editar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
    }
}
