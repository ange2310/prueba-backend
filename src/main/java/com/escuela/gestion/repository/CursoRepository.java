package com.escuela.gestion.repository;

import com.escuela.gestion.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer>{
    
    @Query("SELECT c FROM Curso c " +
           "LEFT JOIN FETCH c.profesor p " +
           "LEFT JOIN FETCH p.persona")
    List<Curso> findAllWithProfesor();
    
    @Query("SELECT c FROM Curso c " +
           "LEFT JOIN FETCH c.profesor p " +
           "LEFT JOIN FETCH p.persona " +
           "WHERE c.idCurso = :id")
    Optional<Curso> findByIdWithProfesor(Integer id);
}