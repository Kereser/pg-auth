package co.com.crediya.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.auth.api.config.security.utils.JwtUtils;
import co.com.crediya.auth.api.dto.LoginDTORequest;
import co.com.crediya.auth.api.dto.LoginDTOResponse;
import co.com.crediya.auth.requestvalidator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthHandler {

  private final ReactiveAuthenticationManager authManager;
  private final RequestValidator validator;
  private final JwtUtils jwtUtils;

  public Mono<ServerResponse> listenLoging(ServerRequest request) {
    return validator
        .validate(request, LoginDTORequest.class, Authentication.class.getSimpleName())
        .flatMap(this::handleAuthenticate)
        .map(this::buildResponse)
        .flatMap(loginResponse -> ServerResponse.ok().bodyValue(loginResponse))
        .onErrorResume(
            e -> {
              log.error("Failed to login. {}", e.getMessage());

              return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            });
  }

  private LoginDTOResponse buildResponse(Authentication authObj) {
    return new LoginDTOResponse(this.jwtUtils.generateToken(authObj));
  }

  private Mono<Authentication> handleAuthenticate(LoginDTORequest dto) {
    return this.authManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()))
        .doOnSubscribe(sub -> log.info("Requesting login with credentials: {}", dto));
  }
}
