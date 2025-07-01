package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;


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

    // Constructor vacío
    public PersonaDTO() {}
    
    // Getters y Setters
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
