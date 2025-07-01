package com.escuela.gestion.service;

import com.escuela.gestion.entity.Profesor;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.repository.ProfesorRepository;
import com.escuela.gestion.exception.ResourceNotFoundException;
import com.escuela.gestion.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    @Autowired
    private PersonaService personaService;
    
    public List<Profesor> findAll() {
        return profesorRepository.findAllWithPersona();
    }
    
    public Page<Profesor> findAllPaged(Pageable pageable) {
        List<Profesor> profesores = profesorRepository.findAllWithPersona();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), profesores.size());
        
        if (start > profesores.size()) {
            return new PageImpl<>(List.of(), pageable, profesores.size());
        }
        
        return new PageImpl<>(profesores.subList(start, end), pageable, profesores.size());
    }
    
    public Profesor findById(Integer id) {
        return profesorRepository.findByIdWithPersona(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + id));
    }
    
    public Optional<Profesor> findByIdOptional(Integer id) {
        return profesorRepository.findByIdWithPersona(id);
    }
    
    @Transactional
    public Profesor save(Profesor profesor) {
        try {
            if (profesor.getIdPersona() == null) {
                if (profesor.getPersona() != null) {
                    // 1. Guardar persona
                    Persona personaGuardada = personaService.save(profesor.getPersona());
                    
                    // 2. Crear profesor con el ID de la persona
                    Profesor nuevoProfesor = new Profesor();
                    nuevoProfesor.setIdPersona(personaGuardada.getIdPersona());
                    nuevoProfesor.setEspecialidad(profesor.getEspecialidad());
                    nuevoProfesor.setFechaContratacion(profesor.getFechaContratacion());
                    
                    // 3. Guardar profesor
                    return profesorRepository.save(nuevoProfesor);
                }
            } else {
                if (profesor.getPersona() != null) {
                    // 1. Actualizar persona
                    profesor.getPersona().setIdPersona(profesor.getIdPersona());
                    personaService.save(profesor.getPersona());
                    
                    // 2. Actualizar solo los campos del profesor
                    Profesor profesorExistente = profesorRepository.findById(profesor.getIdPersona())
                        .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
                    
                    profesorExistente.setEspecialidad(profesor.getEspecialidad());
                    profesorExistente.setFechaContratacion(profesor.getFechaContratacion());
                    
                    return profesorRepository.save(profesorExistente);
                }
                
                // 3. Solo actualizar profesor (sin cambios en persona)
                return profesorRepository.save(profesor);
            }
            
            throw new IllegalArgumentException("Datos del profesor incompletos");
            
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Ya existe un profesor con esos datos");
        }
    }
    
    @Transactional
    public void deleteById(Integer id) {
        if (!profesorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profesor no encontrado con ID: " + id);
        }
        profesorRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return profesorRepository.existsById(id);
    }
}