package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.ModalidadEntrega;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DonacionRequest(
        @NotBlank String titulo,
        @NotBlank String categoria,
        @NotNull @Min(1) Integer cantidad,
        @NotBlank String unidad,
        @NotNull @FutureOrPresent LocalDate fechaVencimiento,
        @NotNull ModalidadEntrega modalidadEntrega,
        String direccionRecojo,
        String horarioDisponible,
        String descripcion,
        String observaciones,
        @NotNull Long donadorId
) {
}
