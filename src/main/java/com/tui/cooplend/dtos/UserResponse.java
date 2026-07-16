package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.Role;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        boolean active) {
}
