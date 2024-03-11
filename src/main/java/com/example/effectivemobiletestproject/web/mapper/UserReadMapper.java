package com.example.effectivemobiletestproject.web.mapper;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.web.dto.account.AccountReadDto;
import com.example.effectivemobiletestproject.web.dto.user.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final AccountReadMapper accountReadMapper;

    @Override
    public UserReadDto map(User object) {
        AccountReadDto accountReadDto = accountReadMapper.map(object.getAccount());
        UserReadDto userReadDto = new UserReadDto();
        userReadDto.setId(object.getId());
        userReadDto.setLogin(object.getLogin());
        userReadDto.setEmail(Optional.ofNullable(object.getEmail()).orElse(null));
        userReadDto.setPhone(Optional.ofNullable(object.getPhone()).orElse(null));
        userReadDto.setBirthday(Optional.ofNullable(object.getBirthday()).orElse(null));
        userReadDto.setFullName(Optional.ofNullable(object.getFullName()).orElse(null));
        userReadDto.setAccount(accountReadDto);
        return userReadDto;
    }
}
