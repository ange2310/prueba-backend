package com.escuela.gestion.repository;

import com.escuela.gestion.entity.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    
    @Query("SELECT e FROM Estudiante e JOIN FETCH e.persona")
    List<Estudiante> findAllWithPersona();
    
    @Query("SELECT e FROM Estudiante e JOIN FETCH e.persona WHERE e.idPersona = :id")
    Optional<Estudiante> findByIdWithPersona(Integer id);
    
    @Query(value = "SELECT e FROM Estudiante e JOIN FETCH e.persona",
           countQuery = "SELECT COUNT(e) FROM Estudiante e")
    Page<Estudiante> findAllWithPersona(Pageable pageable);
}