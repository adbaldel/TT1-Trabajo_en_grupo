# Servidor de Simulaciones - Trabajo en Grupo TT1
**Autores:** Juan Luis Medrano Miguel y Adrián Baldellou Aguirre.

## Descripción del Proyecto
Este proyecto consiste en la implementación de un servidor de simulaciones y un servicio de email. Actúa como el backend (API REST) que procesa las solicitudes de simulación enviadas por nuestra aplicación cliente (desarrollada previamente en Java Spring).

El servidor expone una API sobre HTTP documentada bajo el estándar OpenAPI (Swagger) y está diseñado para gestionar el ciclo de vida de las simulaciones, desde la solicitud inicial hasta la consulta de resultados.

## Tecnologías Utilizadas
* **Lenguaje:** Java
* **Gestor de dependencias:** Maven
* **Arquitectura:** API REST (HTTP)

## Referencia de la API
La capa de presentación responde a las siguientes peticiones HTTP, según la especificación OpenAPI adjunta al proyecto:

### Servicio de Email
* `POST /Email`: Envía un correo electrónico. Requiere los parámetros `emailAddress` y `message`.

### Gestión de Simulaciones (Solicitudes)
* `POST /Solicitud/Solicitar`: Crea una nueva solicitud de simulación para un usuario. Requiere un JSON con las `cantidadesIniciales` y `nombreEntidades`.
* `GET /Solicitud/ComprobarSolicitud`: Comprueba el estado de una simulación específica mediante un token (`tok`).
* `GET /Solicitud/GetSolicitudesUsuario`: Obtiene una lista con todas las solicitudes asociadas a un `nombreUsuario`.

### Resultados
* `POST /Resultados`: Recupera los resultados finales de una simulación completada, requiriendo el `nombreUsuario` y el token de la solicitud (`tok`).

## Instrucciones de Ejecución
Para compilar y empaquetar el proyecto usando Maven:
```bash
mvn clean package