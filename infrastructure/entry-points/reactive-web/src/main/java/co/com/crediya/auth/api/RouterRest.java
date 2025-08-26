package co.com.crediya.auth.api;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.auth.api.config.UserPath;
import co.com.crediya.auth.api.exceptions.ErrorResponseDTO;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
  private final UserPath userPath;
  private final Handler userHandler;

  @Bean
  @RouterOperation(
      method = RequestMethod.POST,
      path = "/api/v1/users",
      beanClass = Handler.class,
      beanMethod = "listCreateUserUseCase",
      operation =
          @Operation(
              operationId = "createUser",
              summary = "Create new user",
              description = "Create a new user while applying validations",
              requestBody =
                  @RequestBody(
                      required = true,
                      description = "User data",
                      content =
                          @Content(
                              mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = CreateUserCommand.class))),
              responses = {
                @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content =
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(
                    responseCode = "400",
                    description = "Not valid requests",
                    content =
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
                @ApiResponse(
                    responseCode = "409",
                    description = "Conflict",
                    content =
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
                @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content =
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
              }))
  public RouterFunction<ServerResponse> routerFunction() {
    return route()
        .path(
            userPath.getBase(),
            builder ->
                builder.POST(
                    userPath.getUsers(),
                    request ->
                        request
                            .bodyToMono(CreateUserCommand.class)
                            .flatMap(userHandler::listCreateUserUseCase)
                            .flatMap(
                                user ->
                                    ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(user))))
        .build();
  }
}
