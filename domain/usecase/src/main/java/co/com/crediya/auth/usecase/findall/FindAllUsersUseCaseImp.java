package co.com.crediya.auth.usecase.findall;

import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindAllUsersUseCaseImp implements FindAllUsersUseCase {
  private final UserRepository userRepository;
  private final UserMapper mapper;

  @Override
  public Flux<UserResponseDTO> execute(FindAllUsersFiltersCommand command) {
    return userRepository.findAllFiltered(command).map(mapper::toDTOResponse);
  }
}
