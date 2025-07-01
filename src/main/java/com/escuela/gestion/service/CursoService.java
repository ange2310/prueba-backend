package com.escuela.gestion.service;

import com.escuela.gestion.entity.Curso;
import com.escuela.gestion.repository.CursoRepository;
import com.escuela.gestion.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    public List<Curso> findAll() {
        return cursoRepository.findAllWithProfesor();
    }
    
    public Page<Curso> findAllPaged(Pageable pageable) {
        List<Curso> cursos = cursoRepository.findAllWithProfesor();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cursos.size());
        
        if (start > cursos.size()) {
            return new PageImpl<>(List.of(), pageable, cursos.size());
        }
        
        return new PageImpl<>(cursos.subList(start, end), pageable, cursos.size());
    }
    
    public Optional<Curso> findById(Integer id) {
        return cursoRepository.findByIdWithProfesor(id);
    }
    
    public Curso findByIdRequired(Integer id) {
        return cursoRepository.findByIdWithProfesor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
    }
    
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    public void deleteById(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
}