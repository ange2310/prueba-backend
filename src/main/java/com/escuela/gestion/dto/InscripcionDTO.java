package com.escuela.gestion.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

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
    
    // Constructor vacío
    public InscripcionDTO() {}
    
    // Getters y Setters
    public Integer getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Integer idInscripcion) { this.idInscripcion = idInscripcion; }
    
    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }
    
    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    
    public EstudianteDTO getEstudiante() { return estudiante; }
    public void setEstudiante(EstudianteDTO estudiante) { this.estudiante = estudiante; }
    
    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }
    
    public String getApellidoEstudiante() { return apellidoEstudiante; }
    public void setApellidoEstudiante(String apellidoEstudiante) { this.apellidoEstudiante = apellidoEstudiante; }
    
    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) { this.numeroMatricula = numeroMatricula; }
    
    public String getGradoEstudiante() { return gradoEstudiante; }
    public void setGradoEstudiante(String gradoEstudiante) { this.gradoEstudiante = gradoEstudiante; }
    
    public CursoDTO getCurso() { return curso; }
    public void setCurso(CursoDTO curso) { this.curso = curso; }
    
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    
    public Integer getCreditosCurso() { return creditosCurso; }
    public void setCreditosCurso(Integer creditosCurso) { this.creditosCurso = creditosCurso; }
    
    public String getNombreProfesor() { return nombreProfesor; }
    public void setNombreProfesor(String nombreProfesor) { this.nombreProfesor = nombreProfesor; }
    
    public String getApellidoProfesor() { return apellidoProfesor; }
    public void setApellidoProfesor(String apellidoProfesor) { this.apellidoProfesor = apellidoProfesor; }
}