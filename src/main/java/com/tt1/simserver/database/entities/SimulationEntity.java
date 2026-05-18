package com.tt1.simserver.database.entities;

import com.tt1.simserver.database.entities.converters.SimulationStatusConverter;
import com.tt1.simserver.model.SimulationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidad de JPA que representa una simulación en la persistencia.
 */
@Entity
@Table(name = "simulation")
public class SimulationEntity {

    @Id
    @Column(name = "token", nullable = false)
    private int token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    @Column(name = "grid_size", nullable = false)
    private int gridSize;

    @Column(name = "status", nullable = false)
    @Convert(converter = SimulationStatusConverter.class)
    private SimulationStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime registerDate;

    /**
     * Construye un resultado de simulación vacío. Todos los campos por defecto (null, 0 o false).
     */
    public SimulationEntity() {
    }

    /**
     * Construye una simulación con el token, usuario, tamaño de tablero y estado pasados como parámetro. Asume que el
     * usuario es no nulo y que el tamaño del tablero es mayor que cero.
     *
     * @param token    el token de la simulación.
     * @param user     el usuario de la persistencia asociado a la simulación.
     * @param gridSize el tamaño del tablero.
     * @param status   el estado de la simulación.
     */
    public SimulationEntity(int token, UserEntity user, int gridSize, SimulationStatus status) {
        this.token = token;
        this.user = user;
        this.gridSize = gridSize;
        this.status = status;
    }

    /**
     * Obtiene el token de esta simulación.
     *
     * @return el token de esta simulación.
     */
    public int getToken() {
        return token;
    }

    /**
     * Cambia el token de esta simulación por {@code token}.
     *
     * @param token el nuevo token.
     */
    public void setToken(int token) {
        this.token = token;
    }

    /**
     * Obtiene el usuario de esta simulación.
     *
     * @return el usuario de esta simulación.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Cambia el usuario de esta simulación por {@code user}. Asume que el usuario es no nulo.
     *
     * @param usuario el nuevo usuario.
     */
    public void setUser(UserEntity usuario) {
        this.user = usuario;
    }

    /**
     * Obtiene el tamaño del tablero de esta simulación.
     *
     * @return el tamaño del tablero de esta simulación.
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Cambia el tamaño del tablero de esta simulación por {@code gridSize}. Asume que el tamaño de tablero es mayor que
     * cero.
     *
     * @param gridSize el nuevo tamaño del tablero.
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Obtiene el estado de esta simulación.
     *
     * @return el estado de esta simulación.
     */
    public SimulationStatus getStatus() {
        return status;
    }

    /**
     * Cambia el estado de la simulación por {@code status}.
     *
     * @param status el nuevo estado.
     */
    public void setStatus(SimulationStatus status) {
        this.status = status;
    }

    /**
     * Obtiene la fecha de registro de esta simulación.
     *
     * @return la fecha de registro de esta simulación.
     */
    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    /**
     * Cambia la fecha de registro de esta simulación por {@code registerDate}.
     *
     * @param registerDate la nueva fecha de registro.
     */
    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }
}