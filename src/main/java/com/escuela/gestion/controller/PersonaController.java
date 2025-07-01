package com.escuela.gestion.controller;

import com.escuela.gestion.dto.PersonaDTO;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.service.PersonaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);
    
    @Autowired
    private PersonaService personaService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<PersonaDTO> getAllPersonas() {
        return personaService.findAll().stream()
                .map(persona -> modelMapper.map(persona, PersonaDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersonaById(@PathVariable Integer id) {
        Persona persona = personaService.findById(id); 
        return ResponseEntity.ok(modelMapper.map(persona, PersonaDTO.class));
    }
    
    @PostMapping
    public PersonaDTO createPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        Persona persona = modelMapper.map(personaDTO, Persona.class);
        Persona savedPersona = personaService.save(persona);
        return modelMapper.map(savedPersona, PersonaDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable Integer id, @Valid @RequestBody PersonaDTO personaDTO) {
        personaDTO.setIdPersona(id);
        Persona persona = modelMapper.map(personaDTO, Persona.class);
        Persona savedPersona = personaService.save(persona);
        return ResponseEntity.ok(modelMapper.map(savedPersona, PersonaDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Integer id) {
        personaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<PersonaDTO> getAllPersonasPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPersona") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        logger.info("Obteniendo personas paginadas - página: {}, tamaño: {}", page, size);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Persona> personasPage = personaService.findAllPaged(pageable);
        
        return personasPage.map(persona -> modelMapper.map(persona, PersonaDTO.class));
    }
}