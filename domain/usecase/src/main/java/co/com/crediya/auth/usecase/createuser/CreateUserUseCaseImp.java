package co.com.crediya.auth.usecase.createuser;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.DuplicatedInfoException;
import co.com.crediya.auth.model.user.exceptions.Fields;
import co.com.crediya.auth.model.user.exceptions.TemplateErrors;
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
    User newUser = userMapper.toDomainFromCommand(command);

    return Mono.when(validateUniqueUser(newUser), validateUniqueIdNumber(newUser))
        .then(Mono.defer(() -> userRepository.save(newUser)))
        .map(userMapper::toDTOResponse);
  }

  private Mono<Void> validateUniqueUser(User user) {
    return userRepository
        .existsByEmail(user.getEmail())
        .flatMap(
            exists ->
                Boolean.TRUE.equals(exists)
                    ? Mono.error(
                        new DuplicatedInfoException(
                            Fields.EMAIL.getName(),
                            TemplateErrors.X_ALREADY_IN_USER.buildMsg(Fields.EMAIL.getName())))
                    : Mono.empty());
  }

  private Mono<Void> validateUniqueIdNumber(User user) {
    return userRepository
        .findByIdNumber(user.getIdNumber())
        .hasElement()
        .flatMap(
            exists ->
                Boolean.TRUE.equals(exists)
                    ? Mono.error(
                        new DuplicatedInfoException(
                            Fields.ID_NUMBER.getName(),
                            TemplateErrors.X_ALREADY_IN_USER.buildMsg(Fields.ID_NUMBER.getName())))
                    : Mono.empty());
  }
}
