package co.com.crediya.auth.api;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.usecase.createuser.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
  private final CreateUserUseCase createUserUseCase;

  @ReadOperation()
  public Mono<UserResponseDTO> listCreateUserUseCase(CreateUserCommand command) {
    return createUserUseCase.execute(command);
  }
}
