package co.com.crediya.auth.model.role.gateways;

import java.util.UUID;

import co.com.crediya.auth.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
  Mono<Role> findById(UUID id);

  Mono<Role> findClientRole();
}
