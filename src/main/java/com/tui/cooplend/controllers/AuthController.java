package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.LoginRequest;
import com.tui.cooplend.dtos.LoginResponse;
import com.tui.cooplend.dtos.UserResponse;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(authService.userResponse(user));
    }
}
