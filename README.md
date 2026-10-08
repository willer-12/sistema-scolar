# Sistema Web de Control de Asistencia Escolar

Proyecto EFSRT II para la I.E. José Faustino Sánchez Carrión – Huamanga. Implementado con Spring Boot, Spring Security, Spring Data JPA, Thymeleaf y Maven.

## Requisitos
- Java 21+
- Maven 3.9+
- Spring Tools for Eclipse (STS) o Eclipse con soporte Maven

## Importar en Spring Tools
1. Descomprime el ZIP.
2. En STS: `File > Import > Maven > Existing Maven Projects`.
3. Selecciona la carpeta raíz que contiene `pom.xml`.
4. Espera la descarga de dependencias y ejecuta `SistemaAsistenciaEscolarApplication.java`.

También puedes ejecutar desde una terminal: `mvn spring-boot:run`.

## URL
http://localhost:8080/login

## Usuarios de prueba
- Administrador: `admin` / `Admin123!`
- Director: `director` / `Director123!`
- Profesor: `profesor` / `Profesor123!`
- Profesor 2: `profesor2` / `Profesor123!`

## Módulos incluidos
- Login y control de acceso por roles.
- Dashboard con indicadores del día.
- Gestión de estudiantes.
- Gestión de grados/secciones y asignación de docente.
- Registro masivo de asistencia (Presente/Tardanza/Falta).
- Justificaciones.
- Reportes con filtros por fechas, sección y estudiante.
- Exportación a Excel (.xlsx) y PDF.
- Base de datos H2 persistente en `./data`.
- Diseño responsivo con HTML + CSS propio.

## Acceso por rol
- ADMINISTRADOR: usuarios, estudiantes, secciones, asistencia, justificaciones, reportes.
- DIRECTOR: dashboard y reportes.
- DOCENTE: dashboard, asistencia de sus secciones, justificaciones y reportes.

El proyecto restringe el acceso a los tres roles solicitados: Profesor/Docente, Administrador y Director. No se habilitó un rol de apoderado en la aplicación, aunque el documento de referencia menciona ese actor en algunos apartados.

## Nota de seguridad
La aplicación usa BCrypt para el hash de contraseñas y Spring Security para autenticación/autorización. En producción se recomienda HTTPS, secretos externos y una base de datos administrada.
