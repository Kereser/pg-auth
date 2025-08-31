package co.com.crediya.auth.r2dbc.role;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.crediya.auth.r2dbc.role.entity.RoleEntity;
import reactor.core.publisher.Mono;

public interface RoleReactiveRepository
    extends ReactiveCrudRepository<RoleEntity, UUID>, ReactiveQueryByExampleExecutor<RoleEntity> {

  @Query("SELECT * from roles WHERE name = 'CLIENT'")
  Mono<RoleEntity> findClientRole();
}
