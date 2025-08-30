package co.com.crediya.auth.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTORequest(
    @Nonnull @NotBlank String email,
    @Nonnull @NotBlank String firstName,
    @Nonnull @NotBlank String lastName,
    @Nonnull LocalDate birthDate,
    String address,
    String phoneNumber,
    @Nonnull BigDecimal baseSalary,
    @Nonnull @NotBlank String idType,
    @Nonnull @NotBlank String idNumber) {}
