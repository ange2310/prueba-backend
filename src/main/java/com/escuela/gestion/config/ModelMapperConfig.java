package com.escuela.gestion.config;

import com.escuela.gestion.dto.*;
import com.escuela.gestion.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        // Configuración para Estudiante -> EstudianteDTO
        mapper.addMappings(new PropertyMap<Estudiante, EstudianteDTO>() {
            @Override
            protected void configure() {
                // Mapear datos de la persona directamente al DTO
                map(source.getPersona().getNombre(), destination.getNombre());
                map(source.getPersona().getApellido(), destination.getApellido());
                map(source.getPersona().getEmail(), destination.getEmail());
                map(source.getPersona().getTelefono(), destination.getTelefono());
                map(source.getPersona().getFechaNacimiento(), destination.getFechaNacimiento());
                
                // Mapear el objeto persona completo
                map(source.getPersona(), destination.getPersona());
            }
        });
        
        // Configuración para Profesor -> ProfesorDTO
        mapper.addMappings(new PropertyMap<Profesor, ProfesorDTO>() {
            @Override
            protected void configure() {
                // Mapear datos de la persona directamente al DTO
                map(source.getPersona().getNombre(), destination.getNombre());
                map(source.getPersona().getApellido(), destination.getApellido());
                map(source.getPersona().getEmail(), destination.getEmail());
                map(source.getPersona().getTelefono(), destination.getTelefono());
                map(source.getPersona().getFechaNacimiento(), destination.getFechaNacimiento());
                
                // Mapear el objeto persona completo
                map(source.getPersona(), destination.getPersona());
            }
        });
        
        // Configuración para Administrativo -> AdministrativoDTO
        mapper.addMappings(new PropertyMap<Administrativo, AdministrativoDTO>() {
            @Override
            protected void configure() {
                // Mapear datos de la persona directamente al DTO
                map(source.getPersona().getNombre(), destination.getNombre());
                map(source.getPersona().getApellido(), destination.getApellido());
                map(source.getPersona().getEmail(), destination.getEmail());
                map(source.getPersona().getTelefono(), destination.getTelefono());
                map(source.getPersona().getFechaNacimiento(), destination.getFechaNacimiento());
                
                // Mapear el objeto persona completo
                map(source.getPersona(), destination.getPersona());
            }
        });
        
        // Configuración para Curso -> CursoDTO
        mapper.addMappings(new PropertyMap<Curso, CursoDTO>() {
            @Override
            protected void configure() {
                // Mapear el ID del profesor si existe
                map(source.getProfesor().getIdPersona(), destination.getIdProfesor());
            }
        });
        
        // Configuración para Inscripcion -> InscripcionDTO
        mapper.addMappings(new PropertyMap<Inscripcion, InscripcionDTO>() {
            @Override
            protected void configure() {
                // Mapear IDs
                map(source.getEstudiante().getIdPersona(), destination.getIdEstudiante());
                map(source.getCurso().getIdCurso(), destination.getIdCurso());
                
                // Mapear objetos completos
                map(source.getEstudiante(), destination.getEstudiante());
                map(source.getCurso(), destination.getCurso());
            }
        });
        
        // Configuración para DTOs -> Entidades (para CREATE/UPDATE)
        mapper.addMappings(new PropertyMap<EstudianteDTO, Estudiante>() {
            @Override
            protected void configure() {
                skip(destination.getPersona());
            }
        });
        
        mapper.addMappings(new PropertyMap<ProfesorDTO, Profesor>() {
            @Override
            protected void configure() {
                skip(destination.getPersona());
            }
        });
        
        mapper.addMappings(new PropertyMap<AdministrativoDTO, Administrativo>() {
            @Override
            protected void configure() {
                skip(destination.getPersona());
            }
        });
        
        mapper.addMappings(new PropertyMap<CursoDTO, Curso>() {
            @Override
            protected void configure() {
                skip(destination.getProfesor());
            }
        });
        
        mapper.addMappings(new PropertyMap<InscripcionDTO, Inscripcion>() {
            @Override
            protected void configure() {
                skip(destination.getEstudiante());
                skip(destination.getCurso());
            }
        });
        
        return mapper;
    }
}