package com.tt1.simserver.database.entities;

import com.tt1.simserver.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entidad de JPA que representa un usuario en la persistencia.
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime registerDate;

    /**
     * Construye un usuario vacío. Todos los campos por defecto (null, 0 o false).
     */
    public UserEntity() {
    }

    /**
     * Construye un usuario con el nombre de usuario {@code username}. El nombre de usuario es válido según
     * {@link User#User(String)}.
     *
     * @param username el nombre de usuario.
     */
    public UserEntity(String username) {
        this.username = username;
    }

    /**
     * Obtiene el nombre de usuario de este usuario.
     *
     * @return el nombre de usuario de este usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Cambia el nombre de usuario de este usuario por {@code username}. El nombre de usuario es válido según
     * {@link User#User(String)}.
     *
     * @param nombreUsuario el nuevo nombre de usuario.
     */
    public void setUsername(String nombreUsuario) {
        this.username = nombreUsuario;
    }

    /**
     * Obtiene la fecha de registro de este usuario.
     *
     * @return la fecha de registro de este usuario.
     */
    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    /**
     * Cambia la fecha de registro de este usuario por {@code registerDate}.
     *
     * @param registerDate la nueva fecha de registro.
     */
    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }
}