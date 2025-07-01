package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "profesor")
public class Profesor {
    
    @Id
    @Column(name = "id_persona")
    private Integer idPersona;
    
    @NotBlank
    @Column(name = "especialidad", length = 200, nullable = false)
    private String especialidad;
    
    @NotNull
    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", insertable = false, updatable = false)
    private Persona persona;
    
    public Profesor() {}
    
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
}