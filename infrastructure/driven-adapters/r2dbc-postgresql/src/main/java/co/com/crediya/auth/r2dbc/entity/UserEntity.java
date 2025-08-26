package co.com.crediya.auth.r2dbc.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("users")
public record UserEntity(
    @Id @Column("user_id") UUID id,
    @NonNull String email,
    @Column("first_name") @NonNull String firstName,
    @Column("last_name") @NonNull String lastName,
    String address,
    @Column("base_salary") @NonNull BigDecimal baseSalary,
    @Column("id_type") @NonNull String idType,
    @Column("id_number") @NonNull String idNumber,
    @Column("birth_date") @NonNull LocalDate birthDate,
    @Column("phone_number") String phoneNumber) {}
