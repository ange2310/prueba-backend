package com.escuela.gestion.controller;

import com.escuela.gestion.dto.CursoDTO;
import com.escuela.gestion.entity.Curso;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public List<CursoDTO> getAllCursos() {
        return cursoService.findAll().stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/paged")
    public Page<CursoDTO> getAllCursosPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCurso") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Curso> cursosPage = cursoService.findAllPaged(pageable);
        
        return cursosPage.map(curso -> modelMapper.map(curso, CursoDTO.class));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> getCursoById(@PathVariable Integer id) {
        Optional<Curso> curso = cursoService.findById(id);
        return curso.map(c -> ResponseEntity.ok(modelMapper.map(c, CursoDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public CursoDTO createCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        Curso savedCurso = cursoService.save(curso);
        return modelMapper.map(savedCurso, CursoDTO.class);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable Integer id, @Valid @RequestBody CursoDTO cursoDTO) {
        cursoDTO.setIdCurso(id);
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        Curso savedCurso = cursoService.save(curso);
        return ResponseEntity.ok(modelMapper.map(savedCurso, CursoDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Integer id) {
        cursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}