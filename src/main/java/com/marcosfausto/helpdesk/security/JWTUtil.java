package com.marcosfausto.helpdesk.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

    }

    public boolean tokenValido(String token) {
        // Claims armazena reivindicações do token ( no caso usuario e tempo de expiração )
        Claims claims = getClaims(token);
        if (claims != null) {
            String userName = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return userName != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    public String getUserName(String token) {
        // Claims armazena reivindicações do token ( no caso usuario e tempo de expiração )
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }
}

