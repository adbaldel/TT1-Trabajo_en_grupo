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
     * Constructor para instanciar a la criatura que se clona indicando un generador
     * aleatorio específico, para fines de consistencia y testeo.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, multiplyProbability entre 0.0 y 1.0 (ambos
     * incluidos), position es no nula, random es no nulo.
     *
     * @param name                el nombre representativo.
     * @param color               el color para el grid de la simulación.
     * @param multiplyProbability el valor base de probabilidad para crear copias en sus adyacencias.
     * @param position            sus coordenadas definitivas y origen reproductor.
     * @param random              el analizador probabilístico de azar para sus comprobaciones biológicas.
     */
    public StaticRabbit(String name, String color, double multiplyProbability, Position position, Random random) {
        super(name, color, position);

        this.multiplyProbability = multiplyProbability;
        this.random = random;
    }

    /**
     * Constructor por defecto usando la librería aleatoria estándar para una criatura multiplicadora.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, multiplyProbability entre 0.0 y 1.0 (ambos
     * incluidos), position es no nula.
     *
     * @param id                  identificador/nombre local de la criatura.
     * @param color               el color que la visibiliza.
     * @param multiplyProbability la probabilidad estática en coma flotante de reproducción.
     * @param position            sus coordenadas iniciales en el tablero.
     */
    public StaticRabbit(String id, String color, double multiplyProbability, Position position) {
        this(id, color, multiplyProbability, position, new Random());
    }


    /**
     * Obtiene el valor probabilístico con el que se rige su descendencia en cada tick temporal.
     *
     * @return un decimal base para intentar generar clones.
     */
    public double getMultiplyProbability() {
        return multiplyProbability;
    }

    /**
     * Lógica de generación probabilística. Determina si este ente creará una instancia
     * hermana/hija de sí mismo buscando casillas a los lados. Si supera la probabilidad,
     * todos los lados posibles (arriba, abajo, izquierda, derecha) son equiprobables al escoger cuna.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid escenario para determinar adyacencias libres.
     * @return la nueva entidad infantil de conseguir clonarse, null de lo contrario.
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
