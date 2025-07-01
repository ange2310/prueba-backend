package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="curso")
public class Curso {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_curso")
    private Integer idCurso;

    @NotBlank
    @Column(name="nombre", length=100, nullable=false)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;
    
    @NotNull
    @Column(name = "creditos", nullable = false)
    private Integer creditos;
    
    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;
    
    // Constructor vacío
    public Curso() {}
    
    // Getters y Setters
    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

}
