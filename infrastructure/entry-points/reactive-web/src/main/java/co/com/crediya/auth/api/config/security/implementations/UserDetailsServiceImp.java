package co.com.crediya.auth.api.config.security.implementations;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.com.crediya.auth.api.config.security.utils.CustomUserDetails;
import co.com.crediya.auth.model.role.gateways.RoleRepository;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.vo.UserEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImp implements ReactiveUserDetailsService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public Mono<UserDetails> findByUsername(String email) {
    return userRepository
        .findByEmail(new UserEmail(email))
        .flatMap(this::enrichUser)
        .map(
            foundUsr -> {
              UserDetails usrDetails =
                  org.springframework.security.core.userdetails.User.builder()
                      .username(foundUsr.getEmail().value())
                      .password(foundUsr.getPassword())
                      .authorities(foundUsr.getRole().getName().name())
                      .build();

              return CustomUserDetails.of(usrDetails, foundUsr.getId());
            });
  }

  private Mono<User> enrichUser(User user) {
    return roleRepository
        .findById(user.getRole().getId())
        .map(role -> user.toBuilder().role(role).build())
        .doOnSubscribe(sub -> log.info("Getting info for userId: {}", user.getId()))
        .doOnSuccess(res -> log.info("Enriched user: {}", res))
        .doOnError(
            err -> log.error("Error while building enrich user. Details: {}", err.getMessage()));
  }
}
