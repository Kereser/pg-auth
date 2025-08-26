package co.com.crediya.auth.model.user.gateways;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.vo.UserEmail;
import reactor.core.publisher.Mono;

public interface UserRepository {
  Mono<User> findByEmail(UserEmail email);

  Mono<Boolean> existsByEmail(UserEmail email);

  Mono<User> save(User entity);
}
