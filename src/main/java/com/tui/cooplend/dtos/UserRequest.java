package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        String passwordHash,
        Role role,
        boolean active
) {
}
