package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "administrativo")
public class Administrativo {
    
    @Id
    @Column(name = "id_persona")
    private Integer idPersona;
    
    @NotBlank
    @Column(name = "cargo", length = 200, nullable = false)
    private String cargo;
    
    @NotBlank
    @Column(name = "departamento", length = 200, nullable = false)
    private String departamento;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", insertable = false, updatable = false)
    private Persona persona;
    
    public Administrativo() {}
    
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
}