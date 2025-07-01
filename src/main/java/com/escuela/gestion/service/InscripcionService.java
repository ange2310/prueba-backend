package com.escuela.gestion.service;

import com.escuela.gestion.entity.Inscripcion;
import com.escuela.gestion.repository.InscripcionRepository;
import com.escuela.gestion.exception.ResourceNotFoundException;
import com.escuela.gestion.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAllWithDetails();
    }
    
    public Page<Inscripcion> findAllPaged(Pageable pageable) {
        List<Inscripcion> inscripciones = inscripcionRepository.findAllWithDetails();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), inscripciones.size());
        
        if (start > inscripciones.size()) {
            return new PageImpl<>(List.of(), pageable, inscripciones.size());
        }
        
        return new PageImpl<>(inscripciones.subList(start, end), pageable, inscripciones.size());
    }
    
    public Inscripcion findById(Integer id) {
        return inscripcionRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + id));
    }
    
    public Optional<Inscripcion> findByIdOptional(Integer id) {
        return inscripcionRepository.findByIdWithDetails(id);
    }
    
    public Inscripcion save(Inscripcion inscripcion) {
        try {
            // Si es una nueva inscripción (sin ID), verificar duplicados
            if (inscripcion.getIdInscripcion() == null) {
                validateUniqueInscripcion(inscripcion);
            }
            
            return inscripcionRepository.save(inscripcion);
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e, inscripcion);
            throw e; 
        }
    }
    
    private void validateUniqueInscripcion(Inscripcion inscripcion) {
        // Buscar si ya existe una inscripción para este estudiante en este curso
        List<Inscripcion> existentes = inscripcionRepository.findAllWithDetails()
                .stream()
                .filter(i -> i.getEstudiante().getIdPersona().equals(inscripcion.getEstudiante().getIdPersona()) &&
                           i.getCurso().getIdCurso().equals(inscripcion.getCurso().getIdCurso()))
                .toList();
        
        if (!existentes.isEmpty()) {
            String nombreEstudiante = inscripcion.getEstudiante().getPersona().getNombre() + " " + 
                                    inscripcion.getEstudiante().getPersona().getApellido();
            String nombreCurso = inscripcion.getCurso().getNombre();
            
            throw new DuplicateResourceException(
                String.format("El estudiante %s ya está inscrito en el curso %s", 
                            nombreEstudiante, nombreCurso));
        }
    }
    
    private void handleDataIntegrityViolation(DataIntegrityViolationException e, Inscripcion inscripcion) {
        String errorMessage = e.getMessage();
        
        if (errorMessage.contains("inscripcion") || errorMessage.contains("unique") || errorMessage.contains("duplicate")) {
            String nombreEstudiante = "Estudiante";
            String nombreCurso = "Curso";
            
            if (inscripcion.getEstudiante() != null && inscripcion.getEstudiante().getPersona() != null) {
                nombreEstudiante = inscripcion.getEstudiante().getPersona().getNombre() + " " + 
                                 inscripcion.getEstudiante().getPersona().getApellido();
            }
            
            if (inscripcion.getCurso() != null) {
                nombreCurso = inscripcion.getCurso().getNombre();
            }
            
            throw new DuplicateResourceException(
                String.format("El estudiante %s ya está inscrito en el curso %s", 
                            nombreEstudiante, nombreCurso));
        }
        
        throw new DuplicateResourceException("Error de integridad de datos: " + errorMessage);
    }
    
    public void deleteById(Integer id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inscripción no encontrada con ID: " + id);
        }
        inscripcionRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return inscripcionRepository.existsById(id);
    }
}