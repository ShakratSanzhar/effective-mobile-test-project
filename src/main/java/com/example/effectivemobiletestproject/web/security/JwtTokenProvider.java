package com.example.effectivemobiletestproject.web.security;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.domain.exception.AccessDeniedException;
import com.example.effectivemobiletestproject.service.UserService;
import com.example.effectivemobiletestproject.service.props.JwtProperties;
import com.example.effectivemobiletestproject.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(
             Long userId,
             String login
    ) {
        Claims claims = Jwts.claims()
                .subject(login)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(
            final Long userId,
            final String login
    ) {
        Claims claims = Jwts.claims()
                .subject(login)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(
            final String refreshToken
    ) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getUserById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setLogin(user.getLogin());
        jwtResponse.setAccessToken(
                createAccessToken(userId, user.getLogin())
        );
        jwtResponse.setRefreshToken(
                createRefreshToken(userId, user.getLogin())
        );
        return jwtResponse;
    }

    public boolean isValid(
            final String token
    ) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }

    private String getId(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }

    private String getLogin(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(
            final String token
    ) {
        String login = getLogin(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                login
        );
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                Collections.EMPTY_LIST
        );
    }

}
