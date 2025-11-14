🧬 API Detector de MutantesEste proyecto es una API REST desarrollada en Spring Boot como trabajo práctico integrador, implementando la lógica para el desafío de detección de mutantes de MercadoLibre.
Información del AlumnoEstudiante: 
  Nicolás Vera
  Legajo: 51146
  Materia: Desarrollo de Software
  Profesor: Cortez Alberto
  Año: 3er Año

Descripción del Proyecto:
La API cumple con los tres niveles del desafío:
  Nivel 1: Un algoritmo optimizado ($O(N^2)$) para detectar más de una secuencia de ADN mutante (horizontal, vertical u oblicua).
  Nivel 2: Exposición de la lógica a través de endpoints REST (/mutant y /stats) y documentación con Swagger.
  ivel 3: Persistencia en base de datos H2, deduplicación de registros usando Hash (SHA-256) y una cobertura de tests superior al 90%. 
Tecnologías Utilizadas:
  Java 21
  Spring Boot 3.5.7
  Spring Data JPA
  H2 Database (Base de datos en memoria)
  SpringDoc OpenAPI (Swagger) (para documentación)
  Lombok
  JUnit 5 & Mockito (para los tests)
  JaCoCo (para cobertura de tests)
  Gradle (como gestor de dependencias) 
Cómo Ejecutar:
Requisitos Previos
  Tener instalado Java JDK 17 o superior (el proyecto fue desarrollado y probado con JDK 21).1. 
  Clonar el Repositorio con el comando: git clone https://github.com/nicofede2509/Integrador-Desarrollo-de-Software-Nicol-s-Vera.git 
  Para Windows deberá ejecutarse el comando: .\gradlew.bat bootRun
  Si se ejecuta en un entorno Linux/MacOS: ./gradlew bootRun
  La aplicación empezará a correr en el puerto 8080 mediante el link: http://localhost:8080

