package com.escuela.gestion.service;

import com.escuela.gestion.entity.Estudiante;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.repository.EstudianteRepository;
import com.escuela.gestion.exception.ResourceNotFoundException;
import com.escuela.gestion.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private PersonaService personaService;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findAllWithPersona();
    }
    
    public Estudiante findById(Integer id) {
        return estudianteRepository.findByIdWithPersona(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
    }
    
    public Optional<Estudiante> findByIdOptional(Integer id) {
        return estudianteRepository.findByIdWithPersona(id);
    }
    
    @Transactional
    public Estudiante save(Estudiante estudiante) {
        try {
            
            if (estudiante.getIdPersona() == null) {
                if (estudiante.getPersona() != null) {
                    Persona personaGuardada = personaService.save(estudiante.getPersona());
                    estudiante.setIdPersona(personaGuardada.getIdPersona());
                    estudiante.setPersona(personaGuardada);
                }
            } else {
                // CASO: Entidad existente
                if (estudiante.getPersona() != null) {
                    // Actualizar persona existente
                    estudiante.getPersona().setIdPersona(estudiante.getIdPersona());
                    Persona personaActualizada = personaService.save(estudiante.getPersona());
                    estudiante.setPersona(personaActualizada);
                }
            }
            
            return estudianteRepository.save(estudiante);
            
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("numero_matricula")) {
                throw new DuplicateResourceException("Ya existe un estudiante con número de matrícula: " + estudiante.getNumeroMatricula());
            }
            throw new DuplicateResourceException("Ya existe un estudiante con esos datos");
        }
    }
    
    @Transactional
    public void deleteById(Integer id) {
        if (!estudianteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return estudianteRepository.existsById(id);
    }

    public Page<Estudiante> findAllPaged(Pageable pageable) {
        return estudianteRepository.findAllWithPersona(pageable);
    }
}