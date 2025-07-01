package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="estudiante")
public class Estudiante {
    @Id
    @Column(name="id_persona")
    private Integer idPersona;

    @NotBlank
    @Column(name="numero_matricula", length=20, nullable=false, unique=true)
    private String numeroMatricula;

    @NotBlank
    @Column(name="grado", length=50, nullable=false)
    private String grado;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    public Estudiante(){}

    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) { this.numeroMatricula = numeroMatricula; }
    
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { 
        this.persona = persona;
        if (persona != null && persona.getIdPersona() != null) {
            this.idPersona = persona.getIdPersona();
        }
    }
}