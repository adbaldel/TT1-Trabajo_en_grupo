package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

import java.util.List;
import java.util.Random;

/**
 * Representa una entidad de la simulación capaz de moverse físicamente
 * por el tablero basándose en una probabilidad definida.
 */
public class MobileCreature extends Creature {
    private final Random random;
    private final double moveProbability;

    /**
     * Instancia la criatura inyectando una semilla aleatoria externa para controlar la probabilidad.
     *
     * <p>Precondición: {@code name} no es nulo ni está vacío. {@code color} es válido en CSS. {@code moveProbability} es un valor entre 0.0 y 1.0 (ambos incluidos). {@code position} y {@code random} no son nulos.
     *
     * <p>Postcondición: Configura la criatura almacenando su probabilidad de movimiento y el generador de azar.
     *
     * @param name            denominación de la especie.
     * @param color           representación gráfica en texto de su color.
     * @param moveProbability probabilidad (0.0 a 1.0) para que la criatura efectúe un movimiento en un turno.
     * @param position        la posición inicial de la criatura.
     * @param random          objeto generador numérico de probabilidad.
     */
    public MobileCreature(String name, String color, double moveProbability, Position position, Random random) {
        super(name, color, position);

        this.moveProbability = moveProbability;
        this.random = random;
    }

    /**
     * Constructor por defecto que asume una librería aleatoria estándar.
     *
     * <p>Precondición: {@code id} no es nulo ni está vacío. {@code color} es válido en CSS. {@code moveProbability} está entre 0.0 y 1.0 (ambos incluidos). {@code position} no es nulo.
     *
     * <p>Postcondición: Construye la criatura autogestionando su propio motor de números aleatorios para calcular el movimiento.
     *
     * @param id              denominación de la especie.
     * @param color           representación gráfica en texto de su color.
     * @param moveProbability probabilidad de moverse en cada turno.
     * @param position        la posición inicial donde se ubicará.
     */
    public MobileCreature(String id, String color, double moveProbability, Position position) {
        this(id, color, moveProbability, position, new Random());
    }

    /**
     * Extrae el ratio matemático configurado que dicta la frecuencia de movimiento en la simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el valor decimal asignado que marca la probabilidad de desplazamiento en cada turno.
     *
     * @return probabilidad del 0.0 al 1.0.
     */
    public double getMoveProbability() {
        return moveProbability;
    }

    /**
     * Resuelve y ejecuta el intento de movimiento de la criatura por el tablero.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura en su posición correspondiente.
     *
     * <p>Postcondición: Si el azar supera la probabilidad definida o si la criatura está rodeada y carece de casillas vacías, devuelve nulo sin modificar su ubicación. Si se cumplen las opciones probabilísticas y hay espacio físico, elige una casilla libre aleatoria, actualiza la posición interna y devuelve estas nuevas coordenadas.
     *
     * @param grid el tablero que rige las colisiones y espacios en este turno.
     * @return la posición de destino a ocupar, o nulo en caso de no desplazarse.
     */
    @Override
    public Position move(GridInterface grid) {
        List<Position> availablePositions;
        Position newPosition = null;

        if (random.nextDouble() <= moveProbability) {
            availablePositions = grid.getAdjacentEmptyCells(getPosition());

            if (!availablePositions.isEmpty()) {
                newPosition = availablePositions.get(random.nextInt(availablePositions.size()));
                position.setX(newPosition.getX());
                position.setY(newPosition.getY());
            }
        }

        return newPosition;
    }

    /**
     * Bloquea el intento biológico de generar crías.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura en su posición correspondiente.
     *
     * <p>Postcondición: Devuelve siempre nulo. Por regla de negocio, la criatura móvil convencional no tiene activada la reproducción y anula el proceso independientemente de las casillas contiguas vacías.
     *
     * @param grid el entorno del tablero evaluado en este turno.
     * @return siempre devuelve nulo.
     */
    @Override
    public Creature multiply(GridInterface grid) {
        return null;
    }
}