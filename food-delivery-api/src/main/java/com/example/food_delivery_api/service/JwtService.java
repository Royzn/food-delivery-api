package com.example.food_delivery_api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretBase64;

    @Getter
    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    public String generateToken(String username, Map<String, Object> claims){
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
