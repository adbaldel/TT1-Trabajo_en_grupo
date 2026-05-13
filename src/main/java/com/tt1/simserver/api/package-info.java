/**
 * Provee de las clases que definen e implementan la API REST de toda la aplicación. Define tres endpoints principales:
 * /Solicitud {@link com.tt1.simserver.api.SimulationRequestController} (con sus sub-endpoints: GET /ComprobarSolicitud,
 * GET /GetSolicitudesUsuario, POST /Solicitar), POST /Email {@link com.tt1.simserver.api.EmailController} y POST
 * /Resultados {@link com.tt1.simserver.api.SimulationResultController}.
 */
package com.tt1.simserver.api;