-- Reconoce caracteres como tildes en nombres y no distingue mayusculas(util para busquedas)
CREATE DATABASE gestion_escolar 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; 

USE gestion_escolar;

CREATE TABLE persona(
	id_persona INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento date NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    telefono VARCHAR(20) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE estudiante(
	id_persona INT NOT NULL PRIMARY KEY,
    numero_matricula VARCHAR(20) UNIQUE NOT NULL,
    grado VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE profesor (
	id_persona INT NOT NULL PRIMARY KEY,
    especialidad VARCHAR(200) NOT NULL,
    fecha_contratacion date NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE administrativo (
	id_persona INT NOT NULL PRIMARY KEY,
    cargo VARCHAR(200) NOT NULL,
    departamento VARCHAR(200) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE curso (
	id_curso INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(250),
    creditos INT NOT NULL,
    id_profesor INT,
    FOREIGN KEY(id_profesor) REFERENCES profesor(id_persona) ON DELETE SET NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE inscripcion(
	id_inscripcion INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_curso INT NOT NULL,
    fecha_inscripcion date NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_persona) ON DELETE CASCADE,
	FOREIGN KEY (id_curso) REFERENCES curso(id_curso) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(200) NOT NULL,
    role ENUM('ADMIN') NOT NULL DEFAULT 'ADMIN',
    is_active BOOLEAN DEFAULT TRUE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SELECT * FROM usuario;

-- DATOS DE PRUEBA
INSERT INTO persona (nombre, apellido, fecha_nacimiento, email, telefono) VALUES
-- Estudiantes (IDs 1-5)
('Juan Carlos', 'Pérez González', '2005-03-15', 'juan.perez@estudiante.escuela.com', '3001234567'),
('María José', 'García López', '2004-07-22', 'maria.garcia@estudiante.escuela.com', '3009876543'),
('Andrés', 'Rodríguez Muñoz', '2005-11-08', 'andres.rodriguez@estudiante.escuela.com', '3005555555'),
('Sofía', 'Martínez Ruiz', '2006-01-14', 'sofia.martinez@estudiante.escuela.com', '3007777777'),
('Carlos Eduardo', 'López Jiménez', '2004-05-30', 'carlos.lopez@estudiante.escuela.com', '3002222222'),

-- Profesores (IDs 6-9)
('Roberto', 'Gómez Peña', '1980-04-18', 'roberto.gomez@profesor.escuela.com', '3011111111'),
('Carmen Rosa', 'Díaz Álvarez', '1975-08-25', 'carmen.diaz@profesor.escuela.com', '3022222222'),
('Fernando', 'Castro Méndez', '1983-12-10', 'fernando.castro@profesor.escuela.com', '3033333333'),
('Liliana', 'Ospina Restrepo', '1978-06-03', 'liliana.ospina@profesor.escuela.com', '3044444444'),

-- Administrativos (IDs 10-12)
('Patricia', 'Salazar Córdoba', '1970-11-20', 'patricia.salazar@admin.escuela.com', '3077777777'),
('Miguel Ángel', 'Torres Guerrero', '1968-02-14', 'miguel.torres@admin.escuela.com', '3088888888'),
('Gloria Elena', 'Ramos Velasco', '1972-07-07', 'gloria.ramos@admin.escuela.com', '3099999999');

INSERT INTO estudiante (id_persona, numero_matricula, grado) VALUES
(1, 'EST2024001', '10°'),
(2, 'EST2024002', '11°'),
(3, 'EST2024003', '10°'),
(4, 'EST2024004', '9°'),
(5, 'EST2024005', '11°');

INSERT INTO profesor (id_persona, especialidad, fecha_contratacion) VALUES
(6, 'Matemáticas y Física', '2015-02-01'),
(7, 'Ciencias Naturales y Química', '2018-08-15'),
(8, 'Historia y Ciencias Sociales', '2020-01-20'),
(9, 'Lengua Castellana y Literatura', '2017-03-10');

INSERT INTO administrativo (id_persona, cargo, departamento) VALUES
(10, 'Coordinadora Académica', 'Académico'),
(11, 'Director de Bienestar Estudiantil', 'Bienestar'),
(12, 'Secretaria de Rectoría', 'Administrativo');

INSERT INTO curso (nombre, descripcion, creditos, id_profesor) VALUES
('Álgebra I', 'Curso introductorio de álgebra para estudiantes de décimo grado', 4, 6),
('Geometría', 'Estudio de formas geométricas y sus propiedades', 4, 6),
('Química', 'Introducción a los conceptos básicos de química', 4, 7),
('Biología', 'Estudio de los seres vivos', 4, 7),
('Historia de Colombia', 'Análisis de los principales eventos históricos del país', 3, 8),
('Literatura', 'Análisis de obras literarias y desarrollo de escritura', 3, 9),
('Educación Física', 'Desarrollo físico y deportivo', 2, NULL);

INSERT INTO inscripcion (id_estudiante, id_curso, fecha_inscripcion) VALUES
-- Juan Carlos (10°) - 4 materias
(1, 1, '2024-02-01'),  -- Álgebra I
(1, 3, '2024-02-01'),  -- Química
(1, 5, '2024-02-01'),  -- Historia de Colombia
(1, 7, '2024-02-01'),  -- Educación Física

-- María José (11°) - 4 materias
(2, 2, '2024-02-01'),  -- Geometría
(2, 4, '2024-02-01'),  -- Biología
(2, 6, '2024-02-01'),  -- Literatura
(2, 7, '2024-02-01'),  -- Educación Física

-- Andrés (10°) - 3 materias
(3, 1, '2024-02-02'),  -- Álgebra I
(3, 3, '2024-02-02'),  -- Química
(3, 7, '2024-02-02'),  -- Educación Física

-- Sofía (9°) - 4 materias
(4, 1, '2024-02-02'),  -- Álgebra I
(4, 4, '2024-02-02'),  -- Biología
(4, 5, '2024-02-02'),  -- Historia de Colombia
(4, 7, '2024-02-02'),  -- Educación Física

-- Carlos Eduardo (11°) - 3 materias
(5, 2, '2024-02-03'),  -- Geometría
(5, 6, '2024-02-03'),  -- Literatura
(5, 7, '2024-02-03');  -- Educación Física

INSERT INTO usuario (username, password, email, role, is_active) VALUES
('patricia.salazar', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'patricia.salazar@admin.escuela.com', 'ADMIN', true),
('miguel.torres', '$2a$10$DowJoayNM3Y..PLuuRd.P.jbdqLV/BsxiBXzOpTk6XzYHs9wCn1YC', 'miguel.torres@admin.escuela.com', 'ADMIN', true),
('gloria.ramos', '$2a$10$E6fiN5ZYStFEHmKhz8.mM.YQD2bKYeWW.3pz/2Ky.4fGmK7sPpN0S', 'gloria.ramos@admin.escuela.com', 'ADMIN', true);

UPDATE usuario SET password = '$2a$10$JUwvQ7qpBJMsLNOX4anq4eDK4QaaWdh8/H8qTzLFSm3ksaWlsBdxC' WHERE username = 'patricia.salazar';
UPDATE usuario SET password = '$2a$10$VTNmmM7d6xaVbAWtWVTQSuFO2f1XP0IlSxocBRdy0ULtriLwdVE5e' WHERE username = 'miguel.torres';
UPDATE usuario SET password = '$2a$10$3F5Bh9/dXjAgKAZB84.G4eQeWcjH3UfV4wUKF4gqGPSDu2fDI8Pv2' WHERE username = 'gloria.ramos';