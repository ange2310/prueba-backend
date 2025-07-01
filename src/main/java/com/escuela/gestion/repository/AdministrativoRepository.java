package com.escuela.gestion.repository;

import com.escuela.gestion.entity.Administrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministrativoRepository extends JpaRepository<Administrativo, Integer>{
    
    @Query("SELECT a FROM Administrativo a JOIN FETCH a.persona")
    List<Administrativo> findAllWithPersona();
    
    @Query("SELECT a FROM Administrativo a JOIN FETCH a.persona WHERE a.idPersona = :id")
    Optional<Administrativo> findByIdWithPersona(Integer id);
}