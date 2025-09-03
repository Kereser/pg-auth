package co.com.crediya.auth.model.user.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String email,
    BigDecimal baseSalary,
    String firstName,
    String idType,
    String idNumber) {}
