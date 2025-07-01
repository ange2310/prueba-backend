package com.escuela.gestion.repository;

import com.escuela.gestion.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    
    @Query("SELECT i FROM Inscripcion i " +
           "JOIN FETCH i.estudiante e " +
           "JOIN FETCH e.persona " +
           "JOIN FETCH i.curso c " +
           "LEFT JOIN FETCH c.profesor p " +
           "LEFT JOIN FETCH p.persona")
    List<Inscripcion> findAllWithDetails();
    
    @Query("SELECT i FROM Inscripcion i " +
           "JOIN FETCH i.estudiante e " +
           "JOIN FETCH e.persona " +
           "JOIN FETCH i.curso c " +
           "LEFT JOIN FETCH c.profesor p " +
           "LEFT JOIN FETCH p.persona " +
           "WHERE i.idInscripcion = :id")
    Optional<Inscripcion> findByIdWithDetails(Integer id);
}