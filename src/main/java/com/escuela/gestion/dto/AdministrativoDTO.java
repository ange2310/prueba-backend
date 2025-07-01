package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AdministrativoDTO {

    private Integer idPersona;

    @NotBlank(message = "Cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "Departamento es obligatorio")
    private String departamento;
    
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
    
    private PersonaDTO persona;

    public AdministrativoDTO(){}

    // Getters y Setters
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public PersonaDTO getPersona() { return persona; }
    public void setPersona(PersonaDTO persona) { this.persona = persona; }
}