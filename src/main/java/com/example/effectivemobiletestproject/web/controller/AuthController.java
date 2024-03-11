package com.example.effectivemobiletestproject.web.controller;

import com.example.effectivemobiletestproject.service.AuthService;
import com.example.effectivemobiletestproject.service.UserService;
import com.example.effectivemobiletestproject.web.dto.auth.JwtRequest;
import com.example.effectivemobiletestproject.web.dto.auth.JwtResponse;
import com.example.effectivemobiletestproject.web.dto.user.UserCreateDto;
import com.example.effectivemobiletestproject.web.dto.user.UserReadDto;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Auth Controller",
        description = "Auth API"
)
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Log in to the system", description = "Log in to the system and receive AccessToken")
    public JwtResponse login(
            @Validated @RequestBody final JwtRequest loginRequest
    ) {
        log.info("Request to log in to the system");
        JwtResponse response = authService.login(loginRequest);
        log.info("Log in successfully");
        return response;
    }

    @PostMapping("/register")
    @Operation(summary = "Register in the system")
    public UserReadDto register(
            @Validated(OnCreate.class)
            @RequestBody final UserCreateDto userDto
    ) {
        log.info("Request to register in the system");
        UserReadDto registeredUser = userService.create(userDto);
        log.info("New user registered");
        return registeredUser;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh your access token")
    public JwtResponse refresh(
            @RequestBody final String refreshToken
    ) {
        log.info("Request to refresh access token");
        JwtResponse response = authService.refresh(refreshToken);
        log.info("Access token successfully refreshed");
        return response;
    }
}
