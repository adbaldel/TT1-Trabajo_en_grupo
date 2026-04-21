# **Servidor de Simulaciones - Trabajo en Grupo TT1**

**Autores:** Juan Luis Medrano Miguel y Adrián Baldellou Aguirre.

## **Descripción del Proyecto**

Este proyecto consiste en el servidor backend que proporciona el núcleo lógico para la ejecución de simulaciones y envío de correos electrónicos.

Actúa como una API REST independiente que procesa en segundo plano las solicitudes computacionales de simulación, gestiona su estado y almacena los resultados para su posterior consulta. La API se expone sobre el protocolo HTTP y está documentada rigurosamente bajo el estándar OpenAPI (Swagger), permitiendo una integración estandarizada con múltiples clientes.

## **Arquitectura y Estructura Principal**

La aplicación está diseñada bajo una arquitectura de capas orientada a servicios utilizando el estándar Jakarta RESTful Web Services (JAX-RS). Su estructura se divide en:

* **Presentación / API (`com.tt1.simserver.presentation`):** Define y expone los endpoints HTTP. Mapea las rutas web a los controladores correspondientes y estandariza las respuestas (ej. `EmailController`, `RequestController`, `ResultsController`).
* **Modelo de Dominio (`com.tt1.simserver.model`):** Clases y registros que definen la estructura de los payloads JSON esperados y enviados en las peticiones HTTP (`Request`, `EmailResponse`, `ProblemDetails`, `ResultsResponse`).
* **Lógica de Negocio / Servicios (`com.tt1.simserver.logic`):** El "cerebro" del servidor. Aquí residen los algoritmos principales de las simulaciones, la lógica para despachar correos y las reglas de negocio del sistema.
* **Persistencia (`com.tt1.simserver.database`):** Repositorios o DAOs encargados de guardar el historial de solicitudes, el estado y los resultados finales de cada simulación (ya sea en memoria o base de datos).

## **Referencia de la API**

El servidor expone y procesa peticiones y respuestas en formato JSON en las siguientes rutas:

### **Servicio de Email**
* `POST /Email`: Envía un correo electrónico. Requiere recibir los parámetros `emailAddress` y `message`.

### **Gestión de Simulaciones (Solicitudes)**
* `POST /Solicitud/Solicitar`: Crea una nueva request de simulación para un usuario. Requiere un payload JSON (`Request`) especificando las `cantidadesIniciales` y `nombreEntidades`. Retorna un `token` identificador.
* `GET /Solicitud/ComprobarSolicitud`: Comprueba el estado de una simulación específica asociando al `nombreUsuario` y el `tok` generado previamente.
* `GET /Solicitud/GetSolicitudesUsuario`: Obtiene una lista con todas las solicitudes / tokens asociados a un `nombreUsuario`.

### **Resultados**
* `POST /Resultados`: Recupera los resultados finales de una simulación finalizada proporcionando el `nombreUsuario` y el `tok`.

## **Tecnologías Utilizadas y Dependencias**

* **Lenguaje:** Java 21
* **Framework Principal:** JAX-RS (Jersey) + Grizzly
* **Gestor de Dependencias:** Maven

Las dependencias principales configuradas en el `pom.xml` incluyen:
* **Jakarta RESTful Web Services API (JAX-RS):** Anotaciones estándar (`@Path`, `@GET`, `@POST`) para el enrutamiento.
* **Jersey Server & HK2:** Implementación oficial del estándar JAX-RS y framework de Inyección de Dependencias.
* **Grizzly HTTP Server:** Servidor HTTP embebido y ligero que permite arrancar la API mediante un `main()` tradicional sin necesidad de contenedores de servlets como Tomcat.
* **Jackson (jersey-media-json-jackson):** Proveedor para la serialización y deserialización automática entre objetos Java y JSON.
* **JUnit 4:** Entorno de pruebas unitarias para la validación de la lógica.

## **Instrucciones de Ejecución**

La aplicación utiliza el `maven-shade-plugin` para crear un ejecutable autocontenido (uber-jar). Para compilar y empaquetar el proyecto, sitúate en la raíz y ejecuta:

```bash
mvn clean package
```  
