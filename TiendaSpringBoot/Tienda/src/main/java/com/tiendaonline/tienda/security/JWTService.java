package com.tiendaonline.tienda.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public String extractUsername(String token) {
        return JWTUtil.extractUsername(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return JWTUtil.isTokenValid(token, userDetails);
    }

    public String extractRole(String token) {
        return JWTUtil.extractRole(token);
    }
}
