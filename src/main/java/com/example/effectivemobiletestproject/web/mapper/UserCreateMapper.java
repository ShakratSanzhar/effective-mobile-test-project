package com.example.effectivemobiletestproject.web.mapper;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.web.dto.user.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto object) {
        User user = new User();
        user.setLogin(object.getLogin());
        user.setPassword(passwordEncoder.encode(object.getPassword()));
        user.setEmail(Optional.ofNullable(object.getEmail()).orElse(null));
        user.setPhone(Optional.ofNullable(object.getPhone()).orElse(null));
        user.setBirthday(Optional.ofNullable(object.getBirthday()).orElse(null));
        user.setFullName(Optional.ofNullable(object.getFullName()).orElse(null));
        return user;
    }
}
