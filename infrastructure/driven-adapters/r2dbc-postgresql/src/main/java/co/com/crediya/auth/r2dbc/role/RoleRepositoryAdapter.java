package co.com.crediya.auth.r2dbc.role;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.model.role.gateways.RoleRepository;
import co.com.crediya.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.auth.r2dbc.role.entity.RoleEntity;
import co.com.crediya.auth.r2dbc.role.mapper.RoleMapperStandard;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class RoleRepositoryAdapter
    extends ReactiveAdapterOperations<Role, RoleEntity, UUID, RoleReactiveRepository>
    implements RoleRepository {
  private final TransactionalOperator txOperator;

  public RoleRepositoryAdapter(
      RoleReactiveRepository repository,
      RoleMapperStandard mapper,
      TransactionalOperator txOperator) {
    super(repository, mapper::toEntity, mapper::toData);
    this.txOperator = txOperator;
  }

  @Override
  public Mono<Role> findClientRole() {
    return this.repository
        .findClientRole()
        .map(super::toEntity)
        .as(txOperator::transactional)
        .doOnSubscribe(sub -> log.info("Finding 'CLIENT' role"))
        .doOnSuccess(res -> log.info("'CLIENT' role found: {}", res))
        .doOnError(
            err -> log.error("Error while retrieve 'CLIENT' role. Reason: {}", err.getMessage()));
  }

  @Override
  public Mono<Role> findById(UUID id) {
    return super.findById(id).as(txOperator::transactional);
  }
}
