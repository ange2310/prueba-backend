package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDTO {

    private Integer idCurso;

    @NotBlank(message = "Nombre del curso es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "Créditos son obligatorios")
    @Min(value = 1,  message = "Mínimo 1 crédito")
    @Max(value = 10, message = "Máximo 10 créditos")
    private Integer creditos;

    private Integer idProfesor;
}
