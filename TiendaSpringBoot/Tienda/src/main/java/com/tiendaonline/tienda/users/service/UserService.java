package com.tiendaonline.tienda.users.service;

import com.tiendaonline.tienda.exceptions.ApiError;
import com.tiendaonline.tienda.exceptions.InvalidInputException;
import com.tiendaonline.tienda.exceptions.NotExistsException;
import com.tiendaonline.tienda.security.JWTService;
import com.tiendaonline.tienda.security.JWTUtil;
import com.tiendaonline.tienda.users.Role;
import com.tiendaonline.tienda.users.dto.UserLoginDTO;
import com.tiendaonline.tienda.users.dto.UserRegisterDTO;
import com.tiendaonline.tienda.users.dto.UserResponseDTO;
import com.tiendaonline.tienda.users.entity.User;
import com.tiendaonline.tienda.users.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDTO register(UserRegisterDTO dto) {

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setRole(Role.USER);

        User uSaved = repository.save(user);

        UserResponseDTO uResponse = new UserResponseDTO();
        uResponse.setId(uSaved.getId());
        uResponse.setEmail(uSaved.getEmail());
        uResponse.setUsername(uSaved.getUsername());
        uResponse.setRole(Role.USER);

        return uResponse;
    }

    public String login(UserLoginDTO dto) {
        User user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NotExistsException("User with email: " + dto.getEmail() + "not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidInputException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
