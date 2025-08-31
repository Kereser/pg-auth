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
import co.com.crediya.auth.api.config.security.implementations.JwtSecurityContextRepository;
import co.com.crediya.auth.api.config.security.implementations.UserDetailsServiceImp;
import co.com.crediya.auth.api.config.security.utils.PasswordEncoderImp;
import co.com.crediya.auth.model.RoleConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

  private final UserDetailsServiceImp userDetailsServiceImp;
  private final JwtSecurityContextRepository securityContextRepository;
  private final Routes routes;

  public static final String SWAGGER_PATH = "/swagger-ui.html";
  public static final String SWAGGER_PATH_1 = "/swagger-ui/**";
  public static final String SWAGGER_PATH_2 = "/v3/api-docs/**";
  public static final String ACTUATOR_PATHS = "/actuator/**";

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authenticationManager(authManager())
        .securityContextRepository(securityContextRepository)
        .exceptionHandling(this::configureExceptionHandling)
        .authorizeExchange(this::configureAuthorization)
        .build();
  }

  private void configureExceptionHandling(
      ServerHttpSecurity.ExceptionHandlingSpec exceptionHandling) {
    exceptionHandling
        .authenticationEntryPoint(
            (swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
        .accessDeniedHandler(
            (swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)));
  }

  private void configureAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec exchanges) {
    exchanges
        .pathMatchers(loginPath())
        .permitAll()
        .pathMatchers(SWAGGER_PATH)
        .permitAll()
        .pathMatchers(SWAGGER_PATH_1)
        .permitAll()
        .pathMatchers(SWAGGER_PATH_2)
        .permitAll()
        .pathMatchers(ACTUATOR_PATHS)
        .permitAll()
        .pathMatchers(HttpMethod.POST, usersPath())
        .hasAnyAuthority(RoleConstants.MANAGER, RoleConstants.ADMIN)
        .anyExchange()
        .authenticated();
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

  private String buildFullPath(String path) {
    return String.format("%s%s", routes.getPaths().getBase(), path);
  }

  private String usersPath() {
    return buildFullPath(routes.getPaths().getUsers());
  }

  private String loginPath() {
    return buildFullPath(routes.getPaths().getLogin());
  }
}
