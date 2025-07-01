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
public class AdministrativoDTO {

    private Integer idPersona;

    @NotBlank(message = "Cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "Departamento es obligatorio")
    private String departamento;

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

    private PersonaDTO persona;
}
