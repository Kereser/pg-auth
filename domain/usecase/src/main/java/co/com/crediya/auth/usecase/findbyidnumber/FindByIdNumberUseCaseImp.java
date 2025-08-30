package co.com.crediya.auth.usecase.findbyidnumber;

import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.*;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.IdNumber;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindByIdNumberUseCaseImp implements FindByIdNumberUseCase {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Mono<UserResponseDTO> execute(IdNumber idNumber) {
    return userRepository
        .findByIdNumber(idNumber)
        .switchIfEmpty(
            Mono.error(
                new EntityNotFoundException(
                    Fields.ID_NUMBER.getName(),
                    TemplateErrors.X_NOT_FOUND_FOR_Y.buildMsg(Entities.USER, idNumber))))
        .map(userMapper::toDTOResponse);
  }
}
