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
public class PersonaDTO {

    private Integer idPersona;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido es obligatorio")
    private String apellido;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    @Past(message = "Fecha de nacimiento no puede ser una fecha futura")
    private LocalDate fechaNacimiento;

    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    @NotBlank(message = "Teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "Teléfono debe tener 10 dígitos")
    private String telefono;
}
