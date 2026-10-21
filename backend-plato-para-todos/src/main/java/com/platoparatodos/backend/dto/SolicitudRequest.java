package com.platoparatodos.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SolicitudRequest(
        @NotNull Long donacionId,
        @NotNull Long beneficiarioId,
        @NotNull @Min(1) Integer cantidadSolicitada,
        LocalDate fechaPropuestaRecojo,
        String motivo
) {
}
