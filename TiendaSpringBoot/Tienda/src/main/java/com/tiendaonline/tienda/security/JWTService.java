package com.tiendaonline.tienda.security;

import com.tiendaonline.tienda.users.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private final JWTUtil jwtUtil;

    public JWTService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtUtil.isTokenValid(token, userDetails);
    }

    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }

    public String generateToken(String email, Role role) {
        return jwtUtil.generateToken(email, role);
    }
}
