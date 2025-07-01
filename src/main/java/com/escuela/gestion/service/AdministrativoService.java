package com.escuela.gestion.service;

import com.escuela.gestion.entity.Administrativo;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.repository.AdministrativoRepository;
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
public class AdministrativoService {
    
    @Autowired
    private AdministrativoRepository administrativoRepository;
    
    @Autowired
    private PersonaService personaService;
    
    public List<Administrativo> findAll() {
        return administrativoRepository.findAllWithPersona();
    }
    
    public Page<Administrativo> findAllPaged(Pageable pageable) {
        List<Administrativo> administrativos = administrativoRepository.findAllWithPersona();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), administrativos.size());
        
        if (start > administrativos.size()) {
            return new PageImpl<>(List.of(), pageable, administrativos.size());
        }
        
        return new PageImpl<>(administrativos.subList(start, end), pageable, administrativos.size());
    }
    
    public Administrativo findById(Integer id) {
        return administrativoRepository.findByIdWithPersona(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrativo no encontrado con ID: " + id));
    }
    
    public Optional<Administrativo> findByIdOptional(Integer id) {
        return administrativoRepository.findByIdWithPersona(id);
    }
    
    @Transactional
    public Administrativo save(Administrativo administrativo) {
        try {
            if (administrativo.getIdPersona() == null) {
                if (administrativo.getPersona() != null) {
                    // 1. Guardar persona
                    Persona personaGuardada = personaService.save(administrativo.getPersona());
                    
                    // 2. Crear administrativo con el ID de la persona
                    Administrativo nuevoAdministrativo = new Administrativo();
                    nuevoAdministrativo.setIdPersona(personaGuardada.getIdPersona());
                    nuevoAdministrativo.setCargo(administrativo.getCargo());
                    nuevoAdministrativo.setDepartamento(administrativo.getDepartamento());
                    
                    // 3. Guardar administrativo
                    return administrativoRepository.save(nuevoAdministrativo);
                }
            } else {
                if (administrativo.getPersona() != null) {

                    administrativo.getPersona().setIdPersona(administrativo.getIdPersona());
                    personaService.save(administrativo.getPersona());

                    Administrativo administrativoExistente = administrativoRepository.findById(administrativo.getIdPersona())
                        .orElseThrow(() -> new ResourceNotFoundException("Administrativo no encontrado"));
                    
                    administrativoExistente.setCargo(administrativo.getCargo());
                    administrativoExistente.setDepartamento(administrativo.getDepartamento());
                    
                    return administrativoRepository.save(administrativoExistente);
                }
                
                return administrativoRepository.save(administrativo);
            }
            
            throw new IllegalArgumentException("Datos del administrativo incompletos");
            
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Ya existe un administrativo con esos datos");
        }
    }
    
    @Transactional
    public void deleteById(Integer id) {
        if (!administrativoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administrativo no encontrado con ID: " + id);
        }
        administrativoRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return administrativoRepository.existsById(id);
    }
}