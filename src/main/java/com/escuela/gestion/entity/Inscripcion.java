package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Integer idInscripcion;
    
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;
    
    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;
    
    @NotNull
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;
    
    // Constructor vacío
    public Inscripcion() {}
    
    // Getters y Setters
    public Integer getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Integer idInscripcion) { this.idInscripcion = idInscripcion; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}

