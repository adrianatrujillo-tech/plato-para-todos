package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.EstadoSolicitud;
import com.platoparatodos.backend.model.Solicitud;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolicitudResponse(
        Long id,
        Long donacionId,
        String donacionTitulo,
        String donacionCategoria,
        Integer donacionCantidad,
        String donacionUnidad,
        String modalidadEntrega,
        String direccionRecojo,
        String horarioDisponible,
        Long donadorId,
        String donadorNombre,
        String donadorOrganizacion,
        Long beneficiarioId,
        String beneficiarioNombre,
        Integer cantidadSolicitada,
        LocalDate fechaPropuestaRecojo,
        String motivo,
        EstadoSolicitud estado,
        LocalDateTime creadoEn
) {
    public static SolicitudResponse from(Solicitud solicitud) {
        return new SolicitudResponse(
                solicitud.getId(),
                solicitud.getDonacion().getId(),
                solicitud.getDonacion().getTitulo(),
                solicitud.getDonacion().getCategoria(),
                solicitud.getDonacion().getCantidad(),
                solicitud.getDonacion().getUnidad(),
                solicitud.getDonacion().getModalidadEntrega().name(),
                solicitud.getDonacion().getDireccionRecojo(),
                solicitud.getDonacion().getHorarioDisponible(),
                solicitud.getDonacion().getDonador().getId(),
                solicitud.getDonacion().getDonador().getNombre(),
                solicitud.getDonacion().getDonador().getOrganizacion(),
                solicitud.getBeneficiario().getId(),
                solicitud.getBeneficiario().getNombre(),
                solicitud.getCantidadSolicitada(),
                solicitud.getFechaPropuestaRecojo(),
                solicitud.getMotivo(),
                solicitud.getEstado(),
                solicitud.getCreadoEn()
        );
    }
}
