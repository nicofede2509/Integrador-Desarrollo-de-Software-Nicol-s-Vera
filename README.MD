# 🧬 API Detector de Mutantes

Este proyecto es una API REST desarrollada en Spring Boot como trabajo práctico integrador, implementando la lógica para el desafío de detección de mutantes de MercadoLibre.

---

## Información personal

* **Estudiante:** Nicolás Vera
* **Legajo:** 51146
* **Materia:** Desarrollo de Software
* **Año:** 3er Año

---

## Descripción del Proyecto

La API cumple con los tres niveles del desafío:
1.  **Nivel 1:** Un algoritmo optimizado ($O(N^2)$) para detectar más de una secuencia de ADN mutante (horizontal, vertical u oblicua).
2.  **Nivel 2:** Exposición de la lógica a través de endpoints REST (`/mutant` y `/stats`) y documentación con Swagger.
3.  **Nivel 3:** Persistencia en base de datos H2, deduplicación de registros usando Hash (SHA-256) y una cobertura de tests superior al 90%.

---

## Tecnologías Utilizadas

* **Java 21**
* **Spring Boot 3.5.7**
* **Spring Data JPA**
* **H2 Database** (Base de datos en memoria)
* **SpringDoc OpenAPI (Swagger)**
* **Lombok**
* **JUnit 5 & Mockito** (Tests)
* **JaCoCo** (Cobertura de tests)
* **Gradle** (Gestor de dependencias)

---
## 🏛️ Arquitectura y Tecnologías Clave

El proyecto está construido siguiendo una arquitectura de 6 capas, separando responsabilidades:

* **`config`**: Configuración de Beans (ej: SwaggerConfig).
* **`controller`**: Recibe las peticiones HTTP (ej: MutantController).
* **`service`**: Contiene toda la lógica de negocio (ej: MutantService, StatsService).
* **`repository`**: Define la interfaz con la base de datos (ej: DnaRecordRepository).
* **`entity`**: Modela las tablas de la base de datos (ej: DnaRecord).
* **`dto`**: Define los objetos de transferencia de datos (JSON) (ej: DnaRequest, StatsResponse).
* **`exception`**: Manejo centralizado de errores (ej: GlobalExceptionHandler).
* **`validation`**: Lógica de validación personalizada (ej: ValidDnaSequenceValidator).

### Docker
El proyecto incluye un `Dockerfile` que empaqueta la aplicación en un contenedor liviano listo para producción. Utiliza un **multi-stage build**, compilando el proyecto en una imagen con JDK 17 y luego copiando el `.jar` final a una imagen limpia de `openjdk:17-alpine`.

**Para construir la imagen de Docker:**
docker build -t mutant-api .


**Para correr el contenedor:**
docker run -p 8080:8080 mutant-api
---
## Cómo instalar el proyecto desde github y correr la aplicación:



### Requisitos Previos
* Tener instalado **Java JDK 17 o superior** (el proyecto fue desarrollado y probado con JDK 21).

### 1. Clonar el Repositorio
* git clone https://github.com/nicofede2509/integrador-Desarrollo-de-Software-Nicol-s-Vera.git
### 2. Ejecutar la Aplicación
El proyecto incluye un Gradle Wrapper, por lo que no necesitás tener Gradle instalado.

**En Windows (PowerShell): .\gradlew.bat bootRun**

**Si el entorno es Linux/MacOS: ./gradelw bootrun**

La aplicación estará corriendo en  el puerto 8080 (`http://localhost:8080`).

### 3. Ejecutar los Tests
Para correr la suite completa de 32 tests (unitarios y de integración) y verificar la cobertura de código:

**En Windows (PowerShell):**
.\gradlew.bat clean test

**En Mac/Linux:**
./gradlew clean test

Si el build termina en **`BUILD SUCCESSFUL`**, significa que todos los tests pasaron y la cobertura de JaCoCo fue superior al 80%.

---

## Como usar la API de forma correcta y segura:

Una vez que la aplicación está corriendo (`.\gradlew.bat bootRun`), podés acceder a los siguientes recursos:

### 1. Documentación de la API (Swagger)
Para ver todos los endpoints de forma interactiva y probarlos:
* **URL:** `http://localhost:8080/swagger-ui.html`

### 2. Consola de la Base de Datos (H2)
Para ver los registros de ADN que se van guardando en la base de datos en memoria:
* **URL:** `http://localhost:8080/h2-console`

**Datos de Conexión:**
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **User Name:** `sa`
* **Password:** (dejar vacío)

### 3. Endpoints Principales

#### `POST /mutant`
Analiza una secuencia de ADN.
* **Respuesta `200 OK`:** Si el ADN es mutante.
* **Respuesta `403 Forbidden`:** Si el ADN es humano.
* **Respuesta `400 Bad Request`:** Si el ADN es inválido (no es NxN, caracteres inválidos, etc.).

#### `GET /stats`
Devuelve las estadísticas de los análisis realizados.
* **Respuesta `200 OK`:** Devuelve un JSON con los conteos y el ratio.
    ```
    {
      "count_human_dna": 0,
      "count_mutant_dna": 0,
      "ratio": 0.0
    }
    ```

