package co.com.crediya.auth.api.config.security.implementations;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import co.com.crediya.auth.api.config.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {
  private static final String BEARER = "Bearer ";
  private static final int TOKEN_SUB_STR_LEN = 7;

  private final JwtUtils jwtUtils;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith(BEARER)) {
      String authToken = authHeader.substring(TOKEN_SUB_STR_LEN);
      if (jwtUtils.validateToken(authToken)) {
        Authentication authentication = jwtUtils.getAuthenticationInfo(authToken);
        return Mono.just(new SecurityContextImpl(authentication));
      }
    }
    return Mono.empty();
  }
}
