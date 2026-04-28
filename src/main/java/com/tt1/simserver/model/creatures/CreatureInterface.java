package com.tt1.simserver.model.creatures;

import com.tt1.simserver.model.Position;

public interface CreatureInterface extends Movable, Multipliable {

    /**
     * Obtiene el nombre de la especie que ha sido asignado a la entidad.
     *
     * @return cadena con el nombre de la criatura.
     */
    String getName();

    /**
     * Obtiene el color de dibujo o representación lógica de esta entidad.
     *
     * @return cadena descriptiva del color.
     */
    String getColor();

    /**
     * Obtiene una copia local de la posición y coordenadas actuales en las que reside esta entidad.
     *
     * @return un objeto clonado de la posición, protegiendo así las variables internas.
     */
    Position getPosition();
}
