
package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.Grid;
import com.tt1.simserver.model.Position;

import java.util.Random;

/**
 * Representa una entidad de la simulación capaz de moverse físicamente
 * por el tablero basándose en una probabilidad definida.
 */
public class MobileCreature extends Creature {
    private Random random;
    private double moveProbability;


    /**
     * Constructor principal permitiendo inyectar una semilla aleatoria externa para control de tests y predictibilidad.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, moveProbability entre 0.0 y 1.0 (ambos
     * incluidos), position es no nula, random es no nulo.
     *
     * @param name denominación de la especie.
     * @param color representación gráfica en texto de su color.
     * @param moveProbability probabilidad (0.0 a 1.0) para que la criatura efectúe un movimiento en un turno.
     * @param position las coordenadas iniciales donde se planta la criatura.
     * @param random objeto generador numérico de probabilidad.
     */
    public MobileCreature(String name, String color, double moveProbability, Position position, Random random) {
        super(name, color, position);

        this.moveProbability = moveProbability;
        this.random = random;
    }

    /**
     * Constructor por defecto instanciando una probabilidad y su propio generador numérico.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, moveProbability entre 0.0 y 1.0 (ambos
     * incluidos), position es no nula.
     *
     * @param id denominación de la especie.
     * @param color representación gráfica en texto de su color.
     * @param moveProbability probabilidad de moverse en cada turno.
     * @param position las coordenadas iniciales donde se ubicará.
     */
    public MobileCreature(String id, String color, double moveProbability, Position position) {
        this(id, color, moveProbability, position, new Random());
    }


    /**
     * Obtiene la probabilidad interna de que la criatura efectúe movimiento durante la simulación.
     *
     * @return probabilidad del 0 al 1.
     */
    public double getMoveProbability() {
        return moveProbability;
    }

    /**
     * Define la mecánica de traslación de la entidad móvil dentro del tablero.
     * Intenta moverse evaluando su probabilidad, y de conseguirlo escoge con
     * la misma probabilidad entre una de las celdas adyacentes vacías disponibles.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid el escenario o cuadrícula para localizar las celdas adyacentes.
     * @return las coordenadas finalmente ocupadas en este turno (puede mantenerse de no cumplirse la probabilidad).
     */
    @Override
    public Position move(Grid grid) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Determina las mecánicas reproductivas de la entidad. Las criaturas móviles convencionales
     * no implementan reproducción y retornarán siempre un nulo.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid entorno de la cuadrícula.
     * @return siempre null para criaturas únicamente móviles.
     */
    @Override
    public Creature multiply(Grid grid) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}