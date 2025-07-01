package com.escuela.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name="persona")
public class Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_persona")
    private Integer idPersona;

    @NotBlank
    @Column(name="nombre", length=100, nullable=false)
    private String nombre;

    @NotBlank 
    @Column(name="apellido", length=100, nullable=false)
    private String apellido;

    @NotNull
    @Column(name="fecha_nacimiento", nullable=false)
    private LocalDate fechaNacimiento;

    @Email
    @NotBlank
    @Column(name="email", length=200, nullable=false, unique=true)
    private String email;

    @NotBlank
    @Column(name="telefono",length=20, nullable=false)
    private String telefono;

    //Constructor vacío
    public Persona(){}
    
    public Integer getIdPersona(){return idPersona; }
    public void setIdPersona(Integer idPersona){this.idPersona=idPersona;}

    public String getNombre(){return nombre; }
    public void setNombre(String nombre){this.nombre=nombre;}

    public String getApellido(){return apellido; }
    public void setApellido(String apellido){this.apellido=apellido;}

    public LocalDate getFechaNacimiento(){return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento){this.fechaNacimiento=fechaNacimiento;}

    public String getEmail(){return email; }
    public void setEmail(String email){this.email=email;}

    public String getTelefono(){return telefono; }
    public void setTelefono(String telefono){this.telefono=telefono;}


}
