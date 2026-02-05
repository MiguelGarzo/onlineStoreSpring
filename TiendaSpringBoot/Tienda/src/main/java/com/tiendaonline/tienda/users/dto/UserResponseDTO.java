package com.tiendaonline.tienda.users.dto;

import com.tiendaonline.tienda.users.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private Role role;
}
