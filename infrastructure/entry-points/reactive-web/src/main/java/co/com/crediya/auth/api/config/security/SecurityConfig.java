package co.com.crediya.auth.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import co.com.crediya.auth.api.config.Routes;
import co.com.crediya.auth.model.RoleConstants;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsServiceImp userDetailsServiceImp;
  private final JwtSecurityContextRepository securityContextRepository;
  private final Routes routes;
  public static final String SWAGGER_URL = "/swagger-ui/**";
  public static final String SWAGGER_HTML = "/swagger-ui.html";
  public static final String SWAGGER_DOCS_URL = "/v3/api-docs/**";
  public static final String ACTUATOR_URL = "/actuator/**";
  private static final String BASE_API_PATH = "/api/v1";

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authenticationManager(authManager())
        .securityContextRepository(securityContextRepository)
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(
                        (swe, e) ->
                            Mono.fromRunnable(
                                () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                    .accessDeniedHandler(
                        (swe, e) ->
                            Mono.fromRunnable(
                                () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))))
        .authorizeExchange(
            exchanges ->
                exchanges
                    .pathMatchers(routes.getPaths().getBase() + routes.getPaths().getLogin())
                    .permitAll()
                    .pathMatchers(HttpMethod.GET, SWAGGER_HTML, SWAGGER_URL, SWAGGER_DOCS_URL)
                    .permitAll()
                    .pathMatchers(ACTUATOR_URL)
                    .permitAll()
                    .pathMatchers(HttpMethod.POST, BASE_API_PATH + routes.getPaths().getUsers())
                    .hasAnyAuthority(RoleConstants.MANAGER, RoleConstants.ADMIN)
                    .anyExchange()
                    .authenticated())
        .build();
  }

  @Bean
  public ReactiveAuthenticationManager authManager() {
    UserDetailsRepositoryReactiveAuthenticationManager manager =
        new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsServiceImp);
    manager.setPasswordEncoder(passwordEncoder());

    return manager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoderImp().getEncoder();
  }
}
