package com.escuela.gestion.repository;

import com.escuela.gestion.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    
    @Query("SELECT p FROM Profesor p JOIN FETCH p.persona")
    List<Profesor> findAllWithPersona();
    
    @Query("SELECT p FROM Profesor p JOIN FETCH p.persona WHERE p.idPersona = :id")
    Optional<Profesor> findByIdWithPersona(Integer id);
}