package com.escuela.gestion.controller;

import com.escuela.gestion.dto.ProfesorDTO;
import com.escuela.gestion.entity.Profesor;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.service.ProfesorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<ProfesorDTO> getAllProfesores() {
        return profesorService.findAll().stream()
                .map(profesor -> modelMapper.map(profesor, ProfesorDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> getProfesorById(@PathVariable Integer id) {
        Profesor profesor = profesorService.findById(id);
        return ResponseEntity.ok(modelMapper.map(profesor, ProfesorDTO.class));
    }
    
    @PostMapping
    public ProfesorDTO createProfesor(@Valid @RequestBody ProfesorDTO profesorDTO) {
        // Crear la entidad Persona desde el DTO
        Persona persona = new Persona();
        persona.setNombre(profesorDTO.getNombre());
        persona.setApellido(profesorDTO.getApellido());
        persona.setEmail(profesorDTO.getEmail());
        persona.setTelefono(profesorDTO.getTelefono());
        persona.setFechaNacimiento(profesorDTO.getFechaNacimiento());
        
        // Crear la entidad Profesor
        Profesor profesor = new Profesor();
        profesor.setEspecialidad(profesorDTO.getEspecialidad());
        profesor.setFechaContratacion(profesorDTO.getFechaContratacion());
        profesor.setPersona(persona);
        
        Profesor savedProfesor = profesorService.save(profesor);
        return modelMapper.map(savedProfesor, ProfesorDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDTO> updateProfesor(@PathVariable Integer id, @Valid @RequestBody ProfesorDTO profesorDTO) {
        Profesor profesorExistente = profesorService.findById(id);
        
        // Actualizar datos de la persona
        Persona persona = profesorExistente.getPersona();
        persona.setNombre(profesorDTO.getNombre());
        persona.setApellido(profesorDTO.getApellido());
        persona.setEmail(profesorDTO.getEmail());
        persona.setTelefono(profesorDTO.getTelefono());
        persona.setFechaNacimiento(profesorDTO.getFechaNacimiento());
        
        // Actualizar datos del profesor
        profesorExistente.setEspecialidad(profesorDTO.getEspecialidad());
        profesorExistente.setFechaContratacion(profesorDTO.getFechaContratacion());
        profesorExistente.setPersona(persona);
        profesorExistente.setIdPersona(id);
        
        Profesor savedProfesor = profesorService.save(profesorExistente);
        return ResponseEntity.ok(modelMapper.map(savedProfesor, ProfesorDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Integer id) {
        profesorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/paged")
    public Page<ProfesorDTO> getAllProfesoresPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPersona") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Profesor> profesoresPage = profesorService.findAllPaged(pageable);
        
        return profesoresPage.map(profesor -> modelMapper.map(profesor, ProfesorDTO.class));
    }
}
