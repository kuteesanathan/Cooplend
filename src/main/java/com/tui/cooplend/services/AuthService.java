package com.tui.cooplend.services;

import com.tui.cooplend.dtos.LoginRequest;
import com.tui.cooplend.dtos.LoginResponse;
import com.tui.cooplend.dtos.UserResponse;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.repositories.UserRepository;
import com.tui.cooplend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService( AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new IllegalStateException("Authenticated user vanished: " + request.email()));
        String token = jwtService.generateToken((UserDetails) user);
        return LoginResponse.of(token, jwtService.getExpirationMillis());
    }

    public UserResponse userResponse(User user){
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
