package co.udea.airline.api.dto;

import java.time.LocalDate;

public record JWTResponseDTO(String email, LocalDate expiresAt, String token) {
    
}
