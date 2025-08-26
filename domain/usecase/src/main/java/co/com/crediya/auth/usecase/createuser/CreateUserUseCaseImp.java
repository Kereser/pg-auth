package co.com.crediya.auth.usecase.createuser;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.DuplicatedInfoException;
import co.com.crediya.auth.model.user.exceptions.ExceptionConsFile;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserUseCaseImp implements CreateUserUseCase {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Mono<UserResponseDTO> execute(CreateUserCommand command) {
    User newUser = userMapper.toDomainCommand(command);

    return userRepository
        .existsByEmail(newUser.getEmail())
        .flatMap(
            foundUser -> {
              if (Boolean.TRUE.equals(foundUser)) {
                throw new DuplicatedInfoException(
                    ExceptionConsFile.EMAIL_FIELD, ExceptionConsFile.EMAIL_ALREADY_IN_USER);
              }

              return userRepository.save(newUser);
            })
        .map(userMapper::toDTO);
  }
}
