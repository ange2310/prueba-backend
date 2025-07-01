package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionDTO {

    private Integer idInscripcion;

    @NotNull(message = "ID de estudiante es obligatorio")
    private Integer idEstudiante;

    @NotNull(message = "ID de curso es obligatorio")
    private Integer idCurso;

    @NotNull(message = "Fecha de inscripción es obligatoria")
    @PastOrPresent(message = "Fecha de inscripción no puede ser futura")
    private LocalDate fechaInscripcion;

    // Información del estudiante
    private EstudianteDTO estudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String numeroMatricula;
    private String gradoEstudiante;

    // Información del curso
    private CursoDTO curso;
    private String nombreCurso;
    private Integer creditosCurso;
    private String nombreProfesor;
    private String apellidoProfesor;
}
