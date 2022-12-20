package com.newgo.mercadoapi.config.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenUtil {
    private static final Integer EXPIRE_DURATION = 24*60*60*1000;

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    public String generateAccessToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.ES256, SECRET_KEY)
                .compact();
    }

    public boolean isTokenExists(String token){
        return Objects.nonNull(token);
    }

    public boolean isTokenStartsWithBearer(String token){
        return token.startsWith("Bearer");
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJwt(token).getBody();
    }

}
