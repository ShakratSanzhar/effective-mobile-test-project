package com.example.effectivemobiletestproject.web.security;

import com.example.effectivemobiletestproject.domain.entity.User;

public final class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getLogin(),
                user.getPassword()
        );
    }
}
