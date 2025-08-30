package co.com.crediya.auth.r2dbc;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.vo.IdNumber;
import co.com.crediya.auth.model.user.vo.UserEmail;
import co.com.crediya.auth.r2dbc.entity.UserEntity;
import co.com.crediya.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.auth.r2dbc.helper.UserMapperStandard;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class UserRepositoryAdapter
    extends ReactiveAdapterOperations<User, UserEntity, UUID, UserReactiveRepository>
    implements UserRepository {
  private final TransactionalOperator txOperator;

  public UserRepositoryAdapter(
      UserReactiveRepository repository,
      UserMapperStandard mapper,
      TransactionalOperator txOperator) {
    super(repository, mapper::toDomain, mapper::toEntity);
    this.txOperator = txOperator;
  }

  @Override
  public Mono<Boolean> existsByEmail(UserEmail email) {
    return repository
        .existsByEmail(email.value())
        .as(txOperator::transactional)
        .doOnSubscribe(sub -> log.info("Checking for email: {}", email))
        .doOnSuccess(res -> log.info("Found email: {}", res))
        .doOnError(
            err ->
                log.error(
                    "Error while trying to found application with email: {}. Details: {}",
                    email,
                    err.getMessage()));
  }

  @Override
  public Mono<User> findByIdNumber(IdNumber idNumber) {
    return repository
        .findByIdNumber(idNumber.value())
        .map(super::toEntity)
        .as(txOperator::transactional)
        .doOnSubscribe(sub -> log.info("Checking for idNumber: {}", idNumber))
        .doOnSuccess(res -> log.info("Found idNumber: {}", res))
        .doOnError(
            err ->
                log.error(
                    "Error while trying to found application with idNumber: {}. Details: {}",
                    idNumber,
                    err.getMessage()));
  }

  @Override
  public Mono<User> save(User entity) {
    return super.save(entity)
        .as(txOperator::transactional)
        .doOnSubscribe(sub -> log.info("Saving user {}", entity))
        .doOnSuccess(res -> log.info("saved user: {}", res))
        .doOnError(
            err -> log.error("Could not save user: {}. Details: {}", entity, err.getMessage()));
  }
}
