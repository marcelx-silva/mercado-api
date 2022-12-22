package com.newgo.mercadoapi.config.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenUtil {
    private static final Integer EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(String username) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(secretKey)
                .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenExists(String token) {
        return Objects.nonNull(token);
    }

    public boolean isTokenStartsWithBearer(String token){
        return token.startsWith("Bearer");
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
    }

}
