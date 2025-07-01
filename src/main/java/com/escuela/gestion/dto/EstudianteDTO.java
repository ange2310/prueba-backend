package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;

public class EstudianteDTO {
    
    private Integer idPersona;
    
    @NotBlank(message = "Número de matrícula es obligatorio")
    @Pattern(regexp = "^EST[0-9]{7}$", message = "Formato: EST2024001")
    private String numeroMatricula;
    
    @NotBlank(message = "Grado es obligatorio")
    private String grado;
    
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
    
    private java.time.LocalDate fechaNacimiento;
    
    // Objeto Persona anidado para facilitar el mapeo
    private PersonaDTO persona;
    
    // Constructor vacío
    public EstudianteDTO() {}
    
    // Getters y Setters
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) { this.numeroMatricula = numeroMatricula; }
    
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public java.time.LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(java.time.LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public PersonaDTO getPersona() { return persona; }
    public void setPersona(PersonaDTO persona) { this.persona = persona; }
}