package com.tui.cooplend.dtos;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {
    public static LoginResponse of(String token, long expiresInSeconds){
        return new LoginResponse(token, "Bearer", expiresInSeconds);
    }
}
