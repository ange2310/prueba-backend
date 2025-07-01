package com.escuela.gestion.controller;

import com.escuela.gestion.dto.EstudianteDTO;
import com.escuela.gestion.entity.Estudiante;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.service.EstudianteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<EstudianteDTO> getAllEstudiantes() {
        return estudianteService.findAll().stream()
                .map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getEstudianteById(@PathVariable Integer id) {
        Estudiante estudiante = estudianteService.findById(id);
        return ResponseEntity.ok(modelMapper.map(estudiante, EstudianteDTO.class));
    }
    
    @PostMapping
    public EstudianteDTO createEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) {

        Persona persona = new Persona();
        persona.setNombre(estudianteDTO.getNombre());
        persona.setApellido(estudianteDTO.getApellido());
        persona.setEmail(estudianteDTO.getEmail());
        persona.setTelefono(estudianteDTO.getTelefono());
        persona.setFechaNacimiento(estudianteDTO.getFechaNacimiento());

        Estudiante estudiante = new Estudiante();
        estudiante.setNumeroMatricula(estudianteDTO.getNumeroMatricula());
        estudiante.setGrado(estudianteDTO.getGrado());
        estudiante.setPersona(persona);
        
        Estudiante savedEstudiante = estudianteService.save(estudiante);
        return modelMapper.map(savedEstudiante, EstudianteDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> updateEstudiante(@PathVariable Integer id, @Valid @RequestBody EstudianteDTO estudianteDTO) {
        Estudiante estudianteExistente = estudianteService.findById(id);
        
        // Crear objeto persona con los datos actualizados
        Persona personaActualizada = new Persona();
        personaActualizada.setIdPersona(id); 
        personaActualizada.setNombre(estudianteDTO.getNombre());
        personaActualizada.setApellido(estudianteDTO.getApellido());
        personaActualizada.setEmail(estudianteDTO.getEmail());
        personaActualizada.setTelefono(estudianteDTO.getTelefono());
        personaActualizada.setFechaNacimiento(estudianteDTO.getFechaNacimiento());
        
        // Actualizar datos del estudiante
        estudianteExistente.setNumeroMatricula(estudianteDTO.getNumeroMatricula());
        estudianteExistente.setGrado(estudianteDTO.getGrado());
        estudianteExistente.setPersona(personaActualizada);
        estudianteExistente.setIdPersona(id);
        
        Estudiante savedEstudiante = estudianteService.save(estudianteExistente);
        return ResponseEntity.ok(modelMapper.map(savedEstudiante, EstudianteDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Integer id) {
        estudianteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<EstudianteDTO> getAllEstudiantesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPersona") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Estudiante> estudiantesPage = estudianteService.findAllPaged(pageable);
        
        return estudiantesPage.map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class));
    }
}