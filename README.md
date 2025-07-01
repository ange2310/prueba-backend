# Gestión Escolar – Backend 

API RESTful para la gestión integral de estudiantes, profesores, administrativos, cursos e inscripciones.

---

## Descripción

Este backend implementa un sistema de gestión escolar con CRUD completo para todas las entidades principales (estudiantes, profesores, administrativos, cursos, inscripciones y personas), autenticación JWT y documentación interactiva vía Swagger.

---

## Tecnologías & dependencias clave

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- Spring Security (JWT)
- MySQL 8+
- ModelMapper
- Lombok
- Swagger/OpenAPI

---

## Requisitos previos

| Requisito | Versión mínima |
|-----------|---------------|
| Java      | 17            |
| Maven     | 3.8           |
| MySQL     | 8             |

---

## Instalación

1. *Clona el repositorio y entra al proyecto:*
   bash
   git clone <https://github.com/ange2310/prueba-backend.git>
   cd gestion-escolar
   

2. *Configura la base de datos:*
   - Crea una base de datos en MySQL llamada gestion_escolar.
   - Ejecuta el script SQL para crear las tablas y datos iniciales, el script sql se encuentra en este mismo repositorio(gestion_escolar.sql)

   - Ajusta las credenciales en src/main/resources/application.properties:
     
     spring.datasource.url=jdbc:mysql://localhost:3306/gestion_escolar
     spring.datasource.username=root
     spring.datasource.password=1234
     

3. *Construye el proyecto:*
   bash
   mvn clean install
   

4. *Ejecuta la aplicación:*
   bash
   mvn spring-boot:run
   
   o bien:
   bash
   java -jar target/gestion-escolar-*.jar

   si usas un IDE como IntelliJ IDEA, Eclipse o VS Code, puedes ejecutar directamente la clase principal `GestionEscolarApplication.java` (que tiene el método `main`). 
   

---

## Ejecución y entorno

- El backend se ejecuta por defecto en [http://localhost:8080]
- La documentación Swagger está disponible en:  
  [http://localhost:8080/swagger-ui/index.html#/]

---

## Endpoints principales

> Todos los endpoints están bajo el prefijo /api/

### Autenticación

| Método | Ruta           | Descripción           | Body de ejemplo                                  |
|--------|----------------|----------------------|--------------------------------------------------|
| POST   | /auth/login    | Login y obtención JWT| { "username": "patricia.salazar", "password": "patricia123" }    |

### Personas

| Método | Ruta                | Descripción                  |
|--------|---------------------|------------------------------|
| GET    | /personas           | Listar todas las personas    |
| GET    | /personas/{id}      | Obtener persona por ID       |
| POST   | /personas           | Crear persona                |
| PUT    | /personas/{id}      | Actualizar persona           |
| DELETE | /personas/{id}      | Eliminar persona             |

### Estudiantes

| Método | Ruta                    | Descripción                  |
|--------|-------------------------|------------------------------|
| GET    | /estudiantes            | Listar estudiantes           |
| GET    | /estudiantes/{id}       | Obtener estudiante por ID    |
| POST   | /estudiantes            | Crear estudiante             |
| PUT    | /estudiantes/{id}       | Actualizar estudiante        |
| DELETE | /estudiantes/{id}       | Eliminar estudiante          |

### Profesores

| Método | Ruta                    | Descripción                  |
|--------|-------------------------|------------------------------|
| GET    | /profesores             | Listar profesores            |
| GET    | /profesores/{id}        | Obtener profesor por ID      |
| POST   | /profesores             | Crear profesor               |
| PUT    | /profesores/{id}        | Actualizar profesor          |
| DELETE | /profesores/{id}        | Eliminar profesor            |

### Administrativos

| Método | Ruta                        | Descripción                  |
|--------|-----------------------------|------------------------------|
| GET    | /administrativos            | Listar administrativos       |
| GET    | /administrativos/{id}       | Obtener administrativo por ID|
| POST   | /administrativos            | Crear administrativo         |
| PUT    | /administrativos/{id}       | Actualizar administrativo    |
| DELETE | /administrativos/{id}       | Eliminar administrativo      |

### Cursos

| Método | Ruta                | Descripción                  |
|--------|---------------------|------------------------------|
| GET    | /cursos             | Listar cursos                |
| GET    | /cursos/{id}        | Obtener curso por ID         |
| POST   | /cursos             | Crear curso                  |
| PUT    | /cursos/{id}        | Actualizar curso             |
| DELETE | /cursos/{id}        | Eliminar curso               |

### Inscripciones

| Método | Ruta                        | Descripción                  |
|--------|-----------------------------|------------------------------|
| GET    | /inscripciones              | Listar inscripciones         |
| GET    | /inscripciones/{id}         | Obtener inscripción por ID   |
| POST   | /inscripciones              | Crear inscripción            |
| PUT    | /inscripciones/{id}         | Actualizar inscripción       |
| DELETE | /inscripciones/{id}         | Eliminar inscripción         |

---

## Ejemplo de login y uso de JWT

bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"1234"}'

Respuesta:
json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}

Luego, usa el token en los endpoints protegidos:
bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/estudiantes


---

## Documentación interactiva

Accede a la documentación y prueba los endpoints desde Swagger UI:  
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## Estructura de carpetas

text
gestion-escolar/
├── src/
│   ├── main/
│   │   ├── java/com/escuela/gestion/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   └── config/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback-spring.xml
│   └── test/
│       └── java/com/escuela/gestion/
├── logs/
├── pom.xml
└── README.md

# Manual de usuario
Se agrega manual de usuario en documento word dentro de este mismo repositorio con credenciales correctas para ingresar y asi probar las funcionalidades
---


## Autor

Autor: Angelica Maria Marcillo
