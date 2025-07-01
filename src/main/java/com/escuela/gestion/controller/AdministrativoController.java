package com.escuela.gestion.controller;

import com.escuela.gestion.dto.AdministrativoDTO;
import com.escuela.gestion.entity.Administrativo;
import com.escuela.gestion.entity.Persona;
import com.escuela.gestion.service.AdministrativoService;
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
@RequestMapping("/api/administrativos")
public class AdministrativoController {
    
    @Autowired
    private AdministrativoService administrativoService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<AdministrativoDTO> getAllAdministrativos() {
        return administrativoService.findAll().stream()
                .map(administrativo -> modelMapper.map(administrativo, AdministrativoDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdministrativoDTO> getAdministrativoById(@PathVariable Integer id) {
        Administrativo administrativo = administrativoService.findById(id);
        return ResponseEntity.ok(modelMapper.map(administrativo, AdministrativoDTO.class));
    }
    
    @PostMapping
    public AdministrativoDTO createAdministrativo(@Valid @RequestBody AdministrativoDTO administrativoDTO) {
        Persona persona = new Persona();
        persona.setNombre(administrativoDTO.getNombre());
        persona.setApellido(administrativoDTO.getApellido());
        persona.setEmail(administrativoDTO.getEmail());
        persona.setTelefono(administrativoDTO.getTelefono());
        persona.setFechaNacimiento(administrativoDTO.getFechaNacimiento());
        
        Administrativo administrativo = new Administrativo();
        administrativo.setCargo(administrativoDTO.getCargo());
        administrativo.setDepartamento(administrativoDTO.getDepartamento());
        administrativo.setPersona(persona);
        
        Administrativo savedAdministrativo = administrativoService.save(administrativo);
        return modelMapper.map(savedAdministrativo, AdministrativoDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AdministrativoDTO> updateAdministrativo(@PathVariable Integer id, @Valid @RequestBody AdministrativoDTO administrativoDTO) {
        Persona persona = new Persona();
        persona.setIdPersona(id); 
        persona.setNombre(administrativoDTO.getNombre());
        persona.setApellido(administrativoDTO.getApellido());
        persona.setEmail(administrativoDTO.getEmail());
        persona.setTelefono(administrativoDTO.getTelefono());
        persona.setFechaNacimiento(administrativoDTO.getFechaNacimiento());
        
        Administrativo administrativo = new Administrativo();
        administrativo.setIdPersona(id); 
        administrativo.setCargo(administrativoDTO.getCargo());
        administrativo.setDepartamento(administrativoDTO.getDepartamento());
        administrativo.setPersona(persona);
        
        Administrativo savedAdministrativo = administrativoService.save(administrativo);
        return ResponseEntity.ok(modelMapper.map(savedAdministrativo, AdministrativoDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrativo(@PathVariable Integer id) {
        administrativoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/paged")
    public Page<AdministrativoDTO> getAllAdministrativosPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPersona") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Administrativo> administrativosPage = administrativoService.findAllPaged(pageable);
        
        return administrativosPage.map(administrativo -> modelMapper.map(administrativo, AdministrativoDTO.class));
    }
}