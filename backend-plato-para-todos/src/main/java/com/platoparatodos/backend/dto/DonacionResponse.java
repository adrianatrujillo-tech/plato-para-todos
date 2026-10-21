package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.EstadoDonacion;
import com.platoparatodos.backend.model.ModalidadEntrega;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DonacionResponse(
        Long id,
        String titulo,
        String categoria,
        Integer cantidad,
        String unidad,
        LocalDate fechaVencimiento,
        ModalidadEntrega modalidadEntrega,
        String direccionRecojo,
        String horarioDisponible,
        String descripcion,
        String observaciones,
        EstadoDonacion estado,
        Long donadorId,
        String donadorNombre,
        String donadorOrganizacion,
        LocalDateTime creadoEn
) {
    public static DonacionResponse from(Donacion donacion) {
        return new DonacionResponse(
                donacion.getId(),
                donacion.getTitulo(),
                donacion.getCategoria(),
                donacion.getCantidad(),
                donacion.getUnidad(),
                donacion.getFechaVencimiento(),
                donacion.getModalidadEntrega(),
                donacion.getDireccionRecojo(),
                donacion.getHorarioDisponible(),
                donacion.getDescripcion(),
                donacion.getObservaciones(),
                donacion.getEstado(),
                donacion.getDonador().getId(),
                donacion.getDonador().getNombre(),
                donacion.getDonador().getOrganizacion(),
                donacion.getCreadoEn()
        );
    }
}
