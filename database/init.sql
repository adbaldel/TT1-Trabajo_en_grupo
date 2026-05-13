-- Script de creación de la Base de Datos para el Servidor de Simulaciones
-- Se ejecutará automáticamente al levantar el contenedor Docker por primera vez.
USE simserver_db;

-- PRINCIPIO: Para poder resetear la base de datos rápido, borrar en la versión final
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS simulation;
DROP TABLE IF EXISTS simulation_result;

SET FOREIGN_KEY_CHECKS=1;
-- FIN: Para poder resetear la base de datos rápido, borrar en la versión final

-- Tabla 1: USER
-- Permite mantener la integridad referencial y preparar el sistema para futuros logins.
CREATE TABLE IF NOT EXISTS user (
    username VARCHAR(50) PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla 2: SIMULATION
-- Guardamos todas las simulaciones de un usuario, acabadas o no.
CREATE TABLE IF NOT EXISTS simulation (
    token INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    grid_size INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES user(username)
);

-- Tabla 3: RESULT
-- Separamos el string masivo en su propia tabla para no penalizar 
-- operaciones de lectura simples (como un COUNT o listados de tokens).
CREATE TABLE IF NOT EXISTS simulation_result (
    token INT PRIMARY KEY,
    ticks INT NOT NULL,
    result_csv LONGTEXT NOT NULL,
    FOREIGN KEY (token) REFERENCES simulation(token)
);