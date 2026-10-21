package com.platoparatodos.backend.dto;

import com.platoparatodos.backend.model.ModalidadEntrega;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DonacionUpdateRequest(
        @NotBlank(message = "El titulo es obligatorio")
        String titulo,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a cero")
        Integer cantidad,

        @NotBlank(message = "La unidad es obligatoria")
        String unidad,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        @FutureOrPresent(message = "La fecha de vencimiento no puede estar en el pasado")
        LocalDate fechaVencimiento,

        @NotNull(message = "La modalidad de entrega es obligatoria")
        ModalidadEntrega modalidadEntrega,

        String direccionRecojo,
        String horarioDisponible,
        String descripcion,
        String observaciones
) {
}
