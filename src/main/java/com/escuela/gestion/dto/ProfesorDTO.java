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
public class ProfesorDTO {

    private Integer idPersona;

    // Datos propios del profesor
    @NotBlank(message = "Especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "Fecha de contratación es obligatoria")
    @PastOrPresent(message = "Fecha de contratación no puede ser futura")
    private LocalDate fechaContratacion;

    // Datos de Persona
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido es obligatorio")
    private String apellido;

    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    @NotBlank(message = "Teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "Teléfono debe tener 10 dígitos")
    private String telefono;

    private LocalDate fechaNacimiento;

    // Objeto Persona anidado para facilitar el mapeo
    private PersonaDTO persona;
}
