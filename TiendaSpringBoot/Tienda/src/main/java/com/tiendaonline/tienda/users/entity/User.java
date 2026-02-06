package com.tiendaonline.tienda.users.entity;

import com.tiendaonline.tienda.users.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Username is required")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
