package com.tt1.simserver.database.transformers;

import com.tt1.simserver.database.entities.UserEntity;
import com.tt1.simserver.model.User;

/**
 * Transformador entre usuarios del modelo de dominio ({@code User}) y usuarios entidad de la persistencia
 * ({@code UserEntity)}.
 */
public class UserTransformer {

    /**
     * Transforma a usuario del modelo de dominio el usuario de la persistencia ({@code userEntity}). Asume que el
     * usuario de la persistencia es no nulo y tiene nombre de usuario válido según {@link User#User(String)}.
     *
     * @param userEntity el usuario de la persistencia a transformar.
     * @return el usuario del modelo de dominio transformado.
     */
    public static User transform(UserEntity userEntity) {
        return new User(userEntity.getUsername());
    }

    /**
     * Transforma a usuario de la persistencia el usuario del modelo de dominio ({@code user}). Asume que el usuario del
     * modelo de dominio es no nulo y tiene nombre de usuario válido según {@link User#User(String)}.
     *
     * @param user el usuario del modelo de dominio a transformar.
     * @return el usuario de la persistencia transformado.
     */
    public static UserEntity transform(User user) {
        return new UserEntity(user.username());
    }
}