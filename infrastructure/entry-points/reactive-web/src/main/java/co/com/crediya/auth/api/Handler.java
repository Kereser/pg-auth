package co.com.crediya.auth.api;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.auth.api.config.UserRoutes;
import co.com.crediya.auth.api.dto.CreateUserDTORequest;
import co.com.crediya.auth.api.helper.UserRestMapper;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.exceptions.MissingRequiredParam;
import co.com.crediya.auth.model.user.vo.IdNumber;
import co.com.crediya.auth.requestvalidator.RequestValidator;
import co.com.crediya.auth.usecase.createuser.CreateUserUseCase;
import co.com.crediya.auth.usecase.findbyidnumber.FindByIdNumberUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
  private final CreateUserUseCase createUserUseCase;
  private final FindByIdNumberUseCase findByIdNumberUseCase;
  private final RequestValidator reqValidator;
  private final UserRestMapper restMapper;
  private final UserRoutes userRoutes;

  @ReadOperation()
  public Mono<ServerResponse> listenCreateUserUseCase(ServerRequest req) {

    return reqValidator
        .validate(req, CreateUserDTORequest.class, User.class.getSimpleName())
        .map(restMapper::toCommand)
        .flatMap(
            command ->
                createUserUseCase
                    .execute(command)
                    .doOnSubscribe(sub -> log.info("Starting creation of user with: "))
                    .doOnSuccess(dto -> log.info("User successfully created: {}", dto))
                    .doOnError(
                        err ->
                            log.error(
                                "Cannot create user for command: {}. Reason: {}",
                                command,
                                err.getMessage())))
        .flatMap(
            user ->
                ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(user));
  }

  public Mono<ServerResponse> listenFindUserByIdNumberUseCase(ServerRequest req) {
    String idNumberVariable;

    try {
      idNumberVariable = req.pathVariable(userRoutes.getVariables().getIdNumber());
    } catch (IllegalArgumentException ex) {
      throw new MissingRequiredParam(
          userRoutes.getVariables().getIdNumber(), "Required to find user by idNumber");
    }

    return findByIdNumberUseCase
        .execute(new IdNumber(idNumberVariable))
        .flatMap(
            usr ->
                ServerResponse.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(usr))
        .doOnSubscribe(
            sub -> log.info("Starting listenFindUserByIdNumberUseCase with: {}", idNumberVariable))
        .doOnSuccess(dto -> log.info("User found for id: {}", idNumberVariable))
        .doOnError(
            err ->
                log.info(
                    "Cannot retrieve user for idNumber: {}. Reason: {}",
                    idNumberVariable,
                    err.getMessage()));
  }
}
