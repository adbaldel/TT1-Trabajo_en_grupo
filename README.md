# **Servidor de Simulaciones \- Trabajo en Grupo TT1**

**Autores:** Juan Luis Medrano Miguel y Adrián Baldellou Aguirre.

[Informe del proyecto (Google Docs)](https://docs.google.com/document/d/12mSnx8gF6DMedae0g9ZdviFhDj2TXrpJABZuXUwqS_0/edit?usp=sharing)

[Documentación del proyecto (Javadoc)](https://adbaldel.github.io/TT1-Trabajo_en_grupo/)

## **¿De qué va este proyecto?**

Este proyecto es un servidor backend (una API REST) creado en Java para la asignatura Taller Transversal I. Su objetivo principal es procesar simulaciones de criaturas en un tablero en segundo plano, gestionar el estado de estas simulaciones y permitir la consulta de resultados mediante un sistema de tokens.

### **La Simulación**

La lógica central consiste en un tablero bidimensional (cuya ocupación inicial se calcula para rondar el 35%) poblado por distintas entidades que interactúan por turnos (de izquierda a derecha y de arriba a abajo). Tenemos 3 tipos de criaturas definidas:

1. **Estáticas:** Se quedan quietas en su casilla.
2. **Móviles:** Se mueven a una casilla adyacente libre basándose en una probabilidad.
3. **Se multiplican:** No se mueven, pero pueden crear una cría clónica en una casilla libre adyacente con cierta probabilidad.

Para evitar que el servidor se bloquee, la ejecución es asíncrona: cuando el usuario pide una simulación, el servidor devuelve rápidamente un `token` identificador y arranca la simulación en un hilo paralelo.

## **Metodología**

El proyecto se está desarrollando siguiendo una **metodología ágil basada en Kanban** y aplicando **TDD (Test-Driven Development)**.

## **Arquitectura**

La aplicación sigue una arquitectura de capas estándar de Jakarta RESTful Web Services (JAX-RS):
* **Presentación / API (`com.tt1.simserver.presentation`):** Expone los endpoints HTTP. Mapea las rutas web a los controladores y estandariza las respuestas.
* **Modelo de Dominio (`com.tt1.simserver.model`):** Clases que definen la estructura de los datos (JSONs) y los actores de la simulación (Tablero, Posiciones, Criaturas).
* **Lógica de Negocio (`com.tt1.simserver.logic`):** El cerebro del servidor. Contiene el motor de simulación, la gestión asíncrona de hilos y las reglas de negocio.
* **Persistencia (`com.tt1.simserver.database`):** Capa encargada de guardar el historial y resultados para ser tolerante a fallos (en desarrollo).

## **Endpoints de la API**

El servidor expone y procesa peticiones en formato JSON (documentado bajo estándar OpenAPI) en las siguientes rutas:

### **Gestión de Simulaciones**

* `POST /Solicitud/Solicitar`: Crea una nueva simulación. Recibe un JSON con los tipos de criaturas y sus cantidades iniciales. Retorna un `token`.
* `GET /Solicitud/ComprobarSolicitud`: Consulta si una simulación en concreto sigue ejecutándose o ya ha terminado (usando el nombre de usuario y el token).
* `GET /Solicitud/GetSolicitudesUsuario`: Obtiene la lista de todos los tokens pertenecientes a un usuario.

### **Resultados**

* `POST /Resultados`: Recupera el historial completo de los pasos del tablero de una simulación finalizada.

### **Extra**

* `POST /Email`: Endpoint para enviar correos electrónicos de notificación.

## **Tecnologías y Dependencias**

* **Lenguaje:** Java 21
* **Framework Web:** JAX-RS (Jersey) \+ Grizzly (Servidor HTTP embebido para poder ejecutar desde un `main()` sin necesidad de Tomcat o equivalente).
* **Serialización:** Jackson (`jersey-media-json-jackson`) para pasar de objetos Java a JSON y viceversa.
* **Testing:** JUnit 5 (Jupiter)
* **Construcción:** Maven (`maven-shade-plugin` para crear un ejecutable *uber-jar* autocontenido).

## **Cómo ejecutar**

Sitúate en la raíz del proyecto y compílalo usando Maven:
```bash
mvn clean package
```

Esto generará un archivo `.jar` ejecutable en la carpeta `target/`. Para arrancar el servidor Grizzly, ejecuta:
```bash
java -jar target/simserver-1.0-SNAPSHOT.jar
```
