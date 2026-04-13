package com.tt1.simserver.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa una solicitud de procesamiento dentro del sistema.
 * Esta clase actúa como Raíz de Agregado (Aggregate Root) y gestiona
 * el ciclo de vida de una petición, desde su creación hasta su finalización.
 * * Asegura la integridad de los datos al validar que cada nombre de entidad
 * tenga su cantidad correspondiente antes de permitir la creación.
 */
public class Solicitud {
    /** Identificador único de negocio para la solicitud (token). */
    private final int token;

    /** Nombre del usuario que realiza la petición. */
    private final String nombreUsuario;

    /** Estado actual de la solicitud en el flujo de trabajo. */
    private EstadoSolicitud estado;

    /** Lista inmutable de ítems (entidad + cantidad) asociados a esta solicitud. */
    private final List<ItemSolicitud> items;

    /** Marca de tiempo de cuándo se creó la solicitud. */
    private final Date fechaCreacion;

    /**
     * Constructor principal que inicializa una solicitud y valida sus parámetros.
     * * @param nombreUsuario Nombre del usuario que lanza la petición.
     * @param cantidades Lista de valores numéricos iniciales.
     * @param nombres Lista de nombres de las entidades a procesar.
     * @throws IllegalArgumentException Si las listas son nulas o no tienen el mismo tamaño.
     */
    public Solicitud(String nombreUsuario, List<Integer> cantidades, List<String> nombres) {
        if (cantidades == null || nombres == null || cantidades.size() != nombres.size()) {
            throw new IllegalArgumentException("Las cantidades y los nombres de entidades deben coincidir y no ser nulos.");
        }

        this.nombreUsuario = nombreUsuario;
        this.token = generarIdUnico();
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fechaCreacion = new Date();
        this.items = new ArrayList<>();

        for (int i = 0; i < cantidades.size(); i++) {
            this.items.add(new ItemSolicitud(nombres.get(i), cantidades.get(i)));
        }
    }

    /**
     * Cambia el estado de la solicitud a 'PROCESANDO'.
     * Este método debe llamarse cuando el motor de cálculo inicia su tarea.
     */
    public void iniciarProcesamiento() {
        if (this.estado == EstadoSolicitud.PENDIENTE) {
            this.estado = EstadoSolicitud.PROCESANDO;
        }
    }

    /**
     * Marca la solicitud como finalizada correctamente.
     * Indica que el proceso ha terminado y los resultados están listos.
     */
    public void finalizarConExito() {
        this.estado = EstadoSolicitud.COMPLETADA;
    }

    /**
     * Genera un identificador numérico aleatorio para la solicitud.
     * * @return Un número entero entre 100000 y 999999.
     */
    private int generarIdUnico() {
        return (int) (Math.random() * 900000) + 100000;
    }

    /**
     * Obtiene el token de identificación.
     * @return Identificador único de la solicitud.
     */
    public int getToken() { return token; }

    /**
     * Obtiene el nombre del usuario.
     * @return Nombre del usuario solicitante.
     */
    public String getNombreUsuario() { return nombreUsuario; }

    /**
     * Obtiene el estado actual de la solicitud.
     * @return El valor del enumerado {@link EstadoSolicitud}.
     */
    public EstadoSolicitud getEstado() { return estado; }

    /**
     * Devuelve los ítems de la solicitud de forma segura para evitar modificaciones externas.
     * @return Una lista de {@link ItemSolicitud} no modificable.
     */
    public List<ItemSolicitud> getItems() { return Collections.unmodifiableList(items); }

    /**
     * Obtiene la fecha de registro.
     * @return Fecha de creación de la instancia.
     */
    public Date getFechaCreacion() { return fechaCreacion; }
}