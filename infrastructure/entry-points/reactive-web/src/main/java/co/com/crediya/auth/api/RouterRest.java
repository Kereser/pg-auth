package co.com.crediya.auth.api;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.auth.api.config.UserRoutes;
import co.com.crediya.auth.api.exceptions.ErrorResponseDTO;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
  private final UserRoutes userRoutes;
  private final Handler userHandler;

  @Bean
  @RouterOperations({
    @RouterOperation(
        method = RequestMethod.GET,
        path = "/api/v1/users/{idNumber}",
        beanClass = Handler.class,
        beanMethod = "listenFindUserByIdNumberUseCase",
        operation =
            @Operation(
                operationId = "findUserByIdNumber",
                summary = "Find user by ID number",
                description = "Retrieve a user using its unique idNumber",
                parameters = {
                  @Parameter(
                      name = "idNumber",
                      description = "The identification number of the user",
                      required = true,
                      in = ParameterIn.PATH,
                      schema = @Schema(type = "string"))
                },
                responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "User found",
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = UserResponseDTO.class))),
                  @ApiResponse(
                      responseCode = "404",
                      description = "User not found",
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = ErrorResponseDTO.class))),
                  @ApiResponse(
                      responseCode = "400",
                      description = "Invalid input",
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
                })),
    @RouterOperation(
        method = RequestMethod.POST,
        path = "/api/v1/users",
        beanClass = Handler.class,
        beanMethod = "listenCreateUserUseCase",
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
                                mediaType =
                                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
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
  })
  public RouterFunction<ServerResponse> routerFunction() {
    return route()
        .path(
            userRoutes.getPaths().getBase(),
            builder ->
                builder
                    .POST(userRoutes.getPaths().getUsers(), userHandler::listenCreateUserUseCase)
                    .GET(
                        userRoutes.getPaths().getUserByIdNumber(),
                        userHandler::listenFindUserByIdNumberUseCase))
        .build();
  }
}
