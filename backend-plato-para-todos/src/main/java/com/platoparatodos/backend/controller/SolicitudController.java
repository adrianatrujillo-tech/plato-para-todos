package com.platoparatodos.backend.controller;

import com.platoparatodos.backend.dto.SolicitudRequest;
import com.platoparatodos.backend.dto.SolicitudResponse;
import com.platoparatodos.backend.dto.SolicitudUpdateRequest;
import com.platoparatodos.backend.model.EstadoSolicitud;
import com.platoparatodos.backend.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public List<SolicitudResponse> listar(
            @RequestParam(required = false) Long beneficiarioId,
            @RequestParam(required = false) Long donadorId
    ) {
        if (beneficiarioId != null) {
            return solicitudService.listarPorBeneficiario(beneficiarioId);
        }

        if (donadorId != null) {
            return solicitudService.listarPorDonador(donadorId);
        }

        return solicitudService.listarTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudResponse crear(@Valid @RequestBody SolicitudRequest request) {
        return solicitudService.crear(request);
    }

    @GetMapping("/{id}")
    public SolicitudResponse obtener(@PathVariable Long id) {
        return solicitudService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public SolicitudResponse editar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudUpdateRequest request
    ) {
        return solicitudService.editar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
    }

    @PatchMapping("/{id}/estado")
    public SolicitudResponse cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoSolicitud estado
    ) {
        return solicitudService.cambiarEstado(id, estado);
    }
}
