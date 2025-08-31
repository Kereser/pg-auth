package co.com.crediya.auth.api.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import co.com.crediya.auth.model.RegexPatterns;

public record LoginDTORequest(
    @Nonnull @NotBlank String email,
    @Nonnull @NotBlank @Pattern(regexp = RegexPatterns.PASSWORD) String password) {}
