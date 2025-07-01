package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;

public class CursoDTO {
    
    private Integer idCurso;
    
    @NotBlank(message = "Nombre del curso es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "Créditos son obligatorios")
    @Min(value = 1, message = "Mínimo 1 crédito")
    @Max(value = 10, message = "Máximo 10 créditos")
    private Integer creditos;
    
    private Integer idProfesor;
    
    // Constructor vacío
    public CursoDTO() {}
    
    // Getters y Setters
    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    
    public Integer getIdProfesor() { return idProfesor; }
    public void setIdProfesor(Integer idProfesor) { this.idProfesor = idProfesor; }
}