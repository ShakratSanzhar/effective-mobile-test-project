package com.example.effectivemobiletestproject.service;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.web.dto.auth.JwtRequest;
import com.example.effectivemobiletestproject.web.dto.auth.JwtResponse;
import com.example.effectivemobiletestproject.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(
            final JwtRequest loginRequest
    ) {
        log.info("Log in starts");
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(), loginRequest.getPassword())
        );
        User user = userService.getByUsername(loginRequest.getLogin());
        jwtResponse.setId(user.getId());
        jwtResponse.setLogin(user.getLogin());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getLogin())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getLogin())
        );
        log.info("Log in ends");
        return jwtResponse;
    }

    public JwtResponse refresh(
            final String refreshToken
    ) {
        log.info("Refreshing access token start");
        JwtResponse response = jwtTokenProvider.refreshUserTokens(refreshToken);
        log.info("Refreshing access token end");
        return response;
    }
}
