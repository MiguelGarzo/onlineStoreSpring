package com.tiendaonline.tienda.users.controller;

import com.tiendaonline.tienda.users.dto.UserLoginDTO;
import com.tiendaonline.tienda.users.dto.UserRegisterDTO;
import com.tiendaonline.tienda.users.dto.UserResponseDTO;
import com.tiendaonline.tienda.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/admin/test")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Admin ok");
    }
}
