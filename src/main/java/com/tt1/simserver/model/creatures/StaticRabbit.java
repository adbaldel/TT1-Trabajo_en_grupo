package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

import java.util.List;
import java.util.Random;

/**
 * Variante de criatura estática que a pesar de no desplazarse de sus coordenadas,
 * tiene la peculiaridad biológica de multiplicar su especie sobre casillas contiguas
 * bajo un sistema probabilístico.
 */
public class StaticRabbit extends StaticCreature {
    private final Random random;
    private final double multiplyProbability;

    /**
     * Inicializa a la criatura indicando un motor aleatorio específico.
     *
     * <p>Precondición: {@code name} no es nulo ni vacío. {@code color} es un color reconocido por CSS. {@code multiplyProbability} es un número entre 0.0 y 1.0 (ambos incluidos). {@code position} y {@code random} no son nulos.
     *
     * <p>Postcondición: Dota a la criatura del gen reproductor, configurando la probabilidad de generar una cría y el motor de azar inyectado.
     *
     * @param name                el nombre representativo de la especie.
     * @param color               el color para mostrar en el tablero de la simulación.
     * @param multiplyProbability el valor base de probabilidad para reproducirse en casillas contiguas.
     * @param position            su posición definitiva que actuará como origen reproductor.
     * @param random              el motor de azar para sus comprobaciones biológicas en cada turno.
     */
    public StaticRabbit(String name, String color, double multiplyProbability, Position position, Random random) {
        super(name, color, position);

        this.multiplyProbability = multiplyProbability;
        this.random = random;
    }

    /**
     * Inicializa a la criatura usando la librería de azar estándar.
     *
     * <p>Precondición: {@code id} no es nulo ni vacío. {@code color} es un color reconocido por CSS. {@code multiplyProbability} es un número entre 0.0 y 1.0 (ambos incluidos). {@code position} no es nulo.
     *
     * <p>Postcondición: Crea el espécimen generando de forma autónoma su propia semilla aleatoria para resolver la reproducción.
     *
     * @param id                  identificador y nombre de la criatura.
     * @param color               el color de renderizado.
     * @param multiplyProbability la probabilidad en coma flotante de reproducirse en este turno.
     * @param position            su posición anclada en el tablero.
     */
    public StaticRabbit(String id, String color, double multiplyProbability, Position position) {
        this(id, color, multiplyProbability, position, new Random());
    }

    /**
     * Provee la métrica estadística que condiciona las posibilidades de gestar crías.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el ratio numérico que se usa como umbral en el cálculo probabilístico de cada turno.
     *
     * @return un valor base del 0.0 al 1.0 para intentar generar clones.
     */
    public double getMultiplyProbability() {
        return multiplyProbability;
    }

    /**
     * Calcula probabilísticamente si debe crear una copia de sí mismo en las inmediaciones.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura en su posición correspondiente.
     *
     * <p>Postcondición: Si no se supera la probabilidad o carece completamente de casillas libres adyacentes, aborta el proceso y devuelve nulo. Si lo consigue y tiene espacio, elige una casilla libre al azar y devuelve una nueva criatura (clon) con el mismo nombre, color, probabilidad de reproducción y vinculada a la nueva posición destino.
     *
     * @param grid escenario del tablero para determinar las casillas adyacentes libres.
     * @return la nueva cría originada, o nulo si no logra reproducirse.
     */
    @Override
    public Creature multiply(GridInterface grid) {
        Creature child = null;
        List<Position> availablePositions;
        Position childPosition;

        if (random.nextDouble() <= multiplyProbability) {
            availablePositions = grid.getAdjacentEmptyCells(getPosition());
            if (!availablePositions.isEmpty()) {
                childPosition = availablePositions.get(random.nextInt(availablePositions.size()));
                child = new StaticRabbit(getName(), getColor(), getMultiplyProbability(), childPosition);
            }
        }

        return child;
    }
}