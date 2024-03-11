package com.example.effectivemobiletestproject.service;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.domain.exception.ResourceNotFoundException;
import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.utils.UserPredicateBuilder;
import com.example.effectivemobiletestproject.web.dto.account.AccountCreateDto;
import com.example.effectivemobiletestproject.web.dto.user.UserCreateDto;
import com.example.effectivemobiletestproject.web.dto.user.UserFilter;
import com.example.effectivemobiletestproject.web.dto.user.UserReadDto;
import com.example.effectivemobiletestproject.web.dto.user.UserUpdateDto;
import com.example.effectivemobiletestproject.web.mapper.UserCreateMapper;
import com.example.effectivemobiletestproject.web.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public Page<UserReadDto> findAll(UserFilter userFilter, Pageable pageable) {
        log.info("Getting users by filter");
        var predicate = new UserPredicateBuilder().build(userFilter);
        Page<User> users = userRepository.findAll(predicate, pageable);
        log.info("Users by filter received");
        return users.map(userReadMapper::map);
    }

    public User getByUsername(String login) {
        log.info("Getting user by login:{}",login);
        return userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public User getUserById(Long id) {
        log.info("Getting user by id:{}",id);
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Transactional
    public UserReadDto update(UserUpdateDto userUpdateDto) {
        log.info("Updating user");
        User existingUser = getUserById(userUpdateDto.getId());
        existingUser.setEmail(userUpdateDto.getEmail());
        existingUser.setPhone(userUpdateDto.getPhone());
        User updatedUser = userRepository.saveAndFlush(existingUser);
        log.info("User updated successfully");
        return userReadMapper.map(updatedUser);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        log.info("Creating new user");
        if(userRepository.findByLogin(userCreateDto.getLogin()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        AccountCreateDto accountCreateDto = userCreateDto.getAccount();
        User newUser = userRepository.saveAndFlush(userCreateMapper.map(userCreateDto));
        log.info("New user with id:{} created",newUser.getId());
        accountCreateDto.setUserId(newUser.getId());
        accountService.create(accountCreateDto);
        log.info("Account to user created");
        return userReadMapper.map(newUser);
    }
}
