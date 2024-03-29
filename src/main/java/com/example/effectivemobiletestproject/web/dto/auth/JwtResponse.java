package com.example.effectivemobiletestproject.web.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {

    private Long id;
    private String login;
    private String accessToken;
    private String refreshToken;
}
