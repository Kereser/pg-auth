package co.com.crediya.auth.model.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserCommand(
    String email,
    String password,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String address,
    String phoneNumber,
    BigDecimal baseSalary,
    String idType,
    String idNumber) {}
