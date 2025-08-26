package co.com.crediya.auth.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.r2dbc")
public record PostgresqlConnectionProperties(
    String host, Integer port, String database, String username, String password) {}
