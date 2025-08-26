package co.com.crediya.auth.r2dbc;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.gateways.UserRepository;
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
    return repository.existsByEmail(email.value()).as(txOperator::transactional);
  }
}
