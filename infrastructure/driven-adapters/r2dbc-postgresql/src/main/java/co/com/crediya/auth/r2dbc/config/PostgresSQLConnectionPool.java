package co.com.crediya.auth.r2dbc.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

@Configuration
public class PostgresSQLConnectionPool {
  public static final int INITIAL_SIZE = 12;
  public static final int MAX_SIZE = 15;
  public static final int MAX_IDLE_TIME = 120;

  @Bean
  public ConnectionPool getConnectionConfig(PostgresqlConnectionProperties properties) {
    PostgresqlConnectionConfiguration dbConfiguration =
        PostgresqlConnectionConfiguration.builder()
            .host(properties.host())
            .port(properties.port())
            .database(properties.database())
            .username(properties.username())
            .password(properties.password())
            .build();

    ConnectionPoolConfiguration poolConfiguration =
        ConnectionPoolConfiguration.builder()
            .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
            .name("api-postgres-connection-pool")
            .initialSize(INITIAL_SIZE)
            .maxSize(MAX_SIZE)
            .maxIdleTime(Duration.ofSeconds(MAX_IDLE_TIME))
            .validationQuery("SELECT 1")
            .build();

    return new ConnectionPool(poolConfiguration);
  }
}
