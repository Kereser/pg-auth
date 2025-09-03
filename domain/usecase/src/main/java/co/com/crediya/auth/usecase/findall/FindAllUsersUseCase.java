package co.com.crediya.auth.usecase.findall;

import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import reactor.core.publisher.Flux;

public interface FindAllUsersUseCase {
  Flux<UserResponseDTO> execute(FindAllUsersFiltersCommand command);
}
