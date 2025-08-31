package co.com.crediya.auth.usecase.createuser;

import co.com.crediya.auth.model.helper.PasswordEncoder;
import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.model.role.RoleName;
import co.com.crediya.auth.model.role.gateways.RoleRepository;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.*;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserUseCaseImp implements CreateUserUseCase {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Override
  public Mono<UserResponseDTO> execute(CreateUserCommand command) {
    User newUser = userMapper.toDomainFromCommand(command);

    return Mono.when(validateUniqueUser(newUser), validateUniqueIdNumber(newUser))
        .then(
            Mono.defer(
                () -> {
                  User user = this.hashPassword(newUser);

                  return saveUserWithClientRole(user);
                }))
        .map(userMapper::toDTOResponse);
  }

  private Mono<User> saveUserWithClientRole(User user) {
    return roleRepository
        .findClientRole()
        .switchIfEmpty(
            Mono.error(
                new EntityNotFoundException(
                    Entities.ROLE.name(),
                    TemplateErrors.X_NOT_FOUND_FOR_Y.buildMsg(
                        Entities.ROLE.name(), RoleName.CLIENT.getName()))))
        .flatMap(role -> saveUserWithGivenRole(user, role));
  }

  private Mono<User> saveUserWithGivenRole(User user, Role role) {
    return userRepository.save(user.toBuilder().role(role).build());
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

  private User hashPassword(User user) {
    return user.toBuilder().password(passwordEncoder.encode(user.getPassword())).build();
  }
}
