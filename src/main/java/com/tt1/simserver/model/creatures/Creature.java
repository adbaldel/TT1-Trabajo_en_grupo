package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.Grid;
import com.tt1.simserver.model.Position;

/**
 * Clase abstracta base que modela a las entidades genéricas dentro de la simulación.
 * Las implementaciones de esta clase definen los diferentes tipos (móviles, estáticos, reproductivos, etc.).
 */
public abstract class Creature {
    private final String name;
    private final String color;
    protected final Position position;


    /**
     * Construye una nueva criatura base configurando sus atributos visuales e iniciales.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, position es no nula.
     *
     * @param name nombre de la especie o de la criatura.
     * @param color el color que la representa visualmente dentro del tablero.
     * @param position sus coordenadas 2D iniciales dentro de la simulación.
     */
    public Creature(String name, String color, Position position) {
        this.name = name;
        this.color = color;
        this.position = position;
    }


    /**
     * Obtiene el nombre de la especie que ha sido asignado a la entidad.
     *
     * @return cadena con el nombre de la criatura.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el color de dibujo o representación lógica de esta entidad.
     *
     * @return cadena descriptiva del color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Obtiene una copia local de la posición y coordenadas actuales en las que reside esta entidad.
     *
     * @return un objeto clonado de la posición, protegiendo así las variables internas.
     */
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    /**
     * Regla abstracta ejecutada durante cada "tick" de la simulación encargada del desplazamiento.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid el tablero y sus casillas vacías adyacentes para evaluar destinos.
     * @return nueva posición de la entidad post-movimiento, o null si decide quedarse quieta o no puede moverse.
     */
    public abstract Position move(Grid grid);

    /**
     * Regla abstracta ejecutada para evaluar una posible clonación o procreación en casillas aledañas.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid el tablero para evaluar la disponibilidad de casillas cercanas de nacimiento.
     * @return una nueva instancia derivada de esta criatura de tener éxito, o null en caso contrario.
     */
    public abstract Creature multiply(Grid grid);
}
