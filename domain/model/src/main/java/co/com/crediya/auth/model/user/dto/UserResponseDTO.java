package co.com.crediya.auth.model.user.dto;

import java.util.UUID;

public record UserResponseDTO(
    UUID id, String email, String firstName, String idType, String idNumber) {}
