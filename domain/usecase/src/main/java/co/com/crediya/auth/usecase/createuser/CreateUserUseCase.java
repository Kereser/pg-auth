package co.com.crediya.auth.usecase.createuser;

import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {
  Mono<UserResponseDTO> execute(CreateUserCommand command);
}
