package com.example.store.services;

import com.example.store.config.JwtConfig;
import com.example.store.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(Integer id, String email, String name, String role){

        //build json web token and have subject
        return generateToken(id, email, name, jwtConfig.getAccessTokenExpiration(),role);
    }

    public String generateRefreshToken(Integer id, String email, String name, String role){
        //build json web token and have subject
        return generateToken(id, email, name, jwtConfig.getRefreshTokenExpiration(),role);
    }

    private String generateToken(Integer id, String email, String name,long tokenExpiration, String role) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("email", email)
                .claim("name", name)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    //validateToken
    public boolean validateToken(String token){
       try {
           var claims = getClaims(token);
           //token is not expired
           return claims.getExpiration().after(new Date());
       }catch (JwtException ex){
           return false;
       }
    }

    private Claims getClaims(String token) {
        var claims = Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public Long getIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    public Role getRoleFromToken(String token){
        return Role.valueOf(getClaims(token).get("role",String.class));
    }

}
