package com.tt1.simserver.modelo;

/**
 * Representa un elemento individual dentro de una solicitud de procesamiento.
 * Al ser un record, esta clase es inmutable y garantiza que los datos de entrada
 * (entidad y cantidad) no varíen una vez creados, asegurando la integridad del dominio.
 * * @param nombreEntidad Nombre identificativo de la entidad a procesar.
 * @param cantidad Valor numérico asociado que no puede ser negativo.
 */
public record ItemSolicitud(String nombreEntidad, int cantidad) {

    /**
     * Constructor compacto para validar la integridad de los datos.
     * * @throws IllegalArgumentException Si la cantidad es inferior a cero.
     */
    public ItemSolicitud {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
    }
}
