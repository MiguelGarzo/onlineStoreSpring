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

// Make the Getters
@Getter

// Make the Setters
@Setter

// Make an empty constructor needed for JPA
@NoArgsConstructor

//Make a full constructor with all the attributes
@AllArgsConstructor

public class User {

    // Define the PrimaryKey as autogenerate in PostgreSQL
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
