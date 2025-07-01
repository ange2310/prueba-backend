package com.escuela.gestion.controller;

import com.escuela.gestion.dto.InscripcionDTO;
import com.escuela.gestion.entity.Inscripcion;
import com.escuela.gestion.entity.Estudiante;
import com.escuela.gestion.entity.Curso;
import com.escuela.gestion.service.InscripcionService;
import com.escuela.gestion.service.EstudianteService;
import com.escuela.gestion.service.CursoService;
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
@RequestMapping("/api/inscripciones")
public class InscripcionController {
    
    @Autowired
    private InscripcionService inscripcionService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<InscripcionDTO> getAllInscripciones() {
        return inscripcionService.findAll().stream()
                .map(inscripcion -> modelMapper.map(inscripcion, InscripcionDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/paged")
    public Page<InscripcionDTO> getAllInscripcionesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idInscripcion") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Inscripcion> inscripcionesPage = inscripcionService.findAllPaged(pageable);
        
        return inscripcionesPage.map(inscripcion -> modelMapper.map(inscripcion, InscripcionDTO.class));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> getInscripcionById(@PathVariable Integer id) {
        Inscripcion inscripcion = inscripcionService.findById(id);
        return ResponseEntity.ok(modelMapper.map(inscripcion, InscripcionDTO.class));
    }
    
    @PostMapping
    public InscripcionDTO createInscripcion(@Valid @RequestBody InscripcionDTO inscripcionDTO) {
        // Obtener las entidades completas desde la base de datos
        Estudiante estudiante = estudianteService.findById(inscripcionDTO.getIdEstudiante());
        Curso curso = cursoService.findByIdRequired(inscripcionDTO.getIdCurso());
        
        // Crear la inscripción manualmente
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());
        
        // Guardar la inscripción
        Inscripcion savedInscripcion = inscripcionService.save(inscripcion);
        return modelMapper.map(savedInscripcion, InscripcionDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> updateInscripcion(@PathVariable Integer id, @Valid @RequestBody InscripcionDTO inscripcionDTO) {
        // Buscar la inscripción existente
        Inscripcion inscripcionExistente = inscripcionService.findById(id);
        
        // Obtener las entidades actualizadas
        Estudiante estudiante = estudianteService.findById(inscripcionDTO.getIdEstudiante());
        Curso curso = cursoService.findByIdRequired(inscripcionDTO.getIdCurso());
        
        // Actualizar los datos
        inscripcionExistente.setEstudiante(estudiante);
        inscripcionExistente.setCurso(curso);
        inscripcionExistente.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());
        
        Inscripcion savedInscripcion = inscripcionService.save(inscripcionExistente);
        return ResponseEntity.ok(modelMapper.map(savedInscripcion, InscripcionDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable Integer id) {
        inscripcionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}