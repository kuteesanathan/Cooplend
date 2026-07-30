package com.tui.cooplend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.cooplend.commonerrors.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), 401, "UNAUTHENTICATED", "A valid Bearer is required to access this resource", request.getRequestURI(), null);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
