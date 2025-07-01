package com.escuela.gestion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.repository.PersonaRepository;
import com.escuela.gestion.exception.ResourceNotFoundException;
import com.escuela.gestion.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonaService.class);

    @Autowired
    private PersonaRepository personaRepository;
    
    public Page<Persona> findAllPaged(Pageable pageable) {
        logger.info("Obteniendo personas paginadas: página {}, tamaño {}", 
            pageable.getPageNumber(), pageable.getPageSize());
        return personaRepository.findAll(pageable);
    }

    public List<Persona> findAll() {
        logger.info("Obteniendo todas las personas");
        List<Persona> personas = personaRepository.findAll();
        logger.info("Se encontraron {} personas", personas.size());
        return personas;
    }
    
    public Persona findById(Integer id) {
        logger.info("Buscando persona con ID: {}", id);
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Persona no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Persona no encontrada con ID: " + id);
                });
        logger.info("Persona encontrada: {} {}", persona.getNombre(), persona.getApellido());
        return persona;
    }
    
    public Optional<Persona> findByIdOptional(Integer id) {
        return personaRepository.findById(id);
    }
    
    public Persona save(Persona persona) {
        logger.info("Guardando persona: {} {}", persona.getNombre(), persona.getApellido());
        try {
            Persona savedPersona = personaRepository.save(persona);
            logger.info("Persona guardada exitosamente con ID: {}", savedPersona.getIdPersona());
            return savedPersona;
        } catch (DataIntegrityViolationException e) {
            logger.error("Error al guardar persona: {}", e.getMessage());
            if (e.getMessage().contains("email")) {
                throw new DuplicateResourceException("Ya existe una persona con el email: " + persona.getEmail());
            }
            throw new DuplicateResourceException("Ya existe una persona con esos datos");
        }
    }
    
    public void deleteById(Integer id) {
        logger.info("Intentando eliminar persona con ID: {}", id);
        if (!personaRepository.existsById(id)) {
            logger.error("No se puede eliminar: Persona no encontrada con ID: {}", id);
            throw new ResourceNotFoundException("Persona no encontrada con ID: " + id);
        }
        personaRepository.deleteById(id);
        logger.info("Persona eliminada exitosamente con ID: {}", id);
    }
    
    public boolean existsById(Integer id) {
        return personaRepository.existsById(id);
    }
}