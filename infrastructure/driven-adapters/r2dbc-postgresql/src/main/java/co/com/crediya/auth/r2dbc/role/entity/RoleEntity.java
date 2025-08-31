package co.com.crediya.auth.r2dbc.role.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.NonNull;

@Table("roles")
@Builder(toBuilder = true)
public record RoleEntity(
    @Id @Column("role_id") UUID id, @NonNull String name, @NonNull String description) {}
