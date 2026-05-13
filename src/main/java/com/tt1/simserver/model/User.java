package com.tt1.simserver.model;

/**
 * Representa un usuario (inmutable).
 *
 * @param username el nombre del usuario.
 */
public record User(String username) {

    /**
     * Construye un usuario con el nombre dado. Asume que el nombre de usuario es no nulo y no blank.
     *
     * @param username el nombre de usuario.
     */
    public User {
    }

    /**
     * Obtiene el nombre de este usuario.
     *
     * @return el nombre de este usuario.
     */
    @Override
    public String username() {
        return username;
    }

    /**
     * Comprueba si este usuario es igual al objeto {@code o}. Devuelve cierto si el objeto es un usuario con el mismo
     * nombre.
     *
     * @param o el objeto a comparar.
     * @return cierto si este usuario es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return username.equals(user.username);
    }
}