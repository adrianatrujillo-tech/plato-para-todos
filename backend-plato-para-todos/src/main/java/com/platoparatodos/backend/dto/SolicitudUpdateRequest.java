package com.platoparatodos.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SolicitudUpdateRequest(
        @NotNull @Min(1) Integer cantidadSolicitada,
        LocalDate fechaPropuestaRecojo,
        String motivo
) {
}
