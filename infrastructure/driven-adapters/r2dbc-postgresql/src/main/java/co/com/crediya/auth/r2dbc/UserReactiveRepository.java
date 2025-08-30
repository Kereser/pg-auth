package co.com.crediya.auth.r2dbc;

import java.util.UUID;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.crediya.auth.r2dbc.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository
    extends ReactiveCrudRepository<UserEntity, UUID>, ReactiveQueryByExampleExecutor<UserEntity> {
  Mono<Boolean> existsByEmail(String email);

  Mono<UserEntity> findByIdNumber(String idNumber);
}
