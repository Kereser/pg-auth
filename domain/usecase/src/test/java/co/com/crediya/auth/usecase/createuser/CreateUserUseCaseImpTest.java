package co.com.crediya.auth.usecase.createuser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.DuplicatedInfoException;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.FirstName;
import co.com.crediya.auth.model.user.vo.UserEmail;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImpTest {
  static {
    BlockHound.install();
  }

  @Mock private UserRepository userRepository;

  @Mock private UserMapper userMapper;

  @InjectMocks private CreateUserUseCaseImp createUserUseCase;

  private CreateUserCommand command;
  private User userDomain;
  private UserResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    command =
        new CreateUserCommand(
            "test@test.com",
            "John",
            "Doe",
            LocalDate.now(),
            "123 Street",
            "555-1234",
            BigDecimal.TEN,
            "CC",
            "12345");

    userDomain =
        User.builder()
            .id(UUID.randomUUID())
            .email(new UserEmail("test@test.com"))
            .firstName(new FirstName("John"))
            .build();

    responseDTO =
        new UserResponseDTO(
            userDomain.getId(),
            userDomain.getEmail().value(),
            userDomain.getFirstName().value(),
            "CC",
            "12345");
  }

  @Test
  void shouldCreateUserSuccessfullyWhenEmailDoesNotExist() {
    Mockito.when(userMapper.toDomainCommand(command)).thenReturn(userDomain);
    Mockito.when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(false));
    Mockito.when(userRepository.save(userDomain)).thenReturn(Mono.just(userDomain));
    Mockito.when(userMapper.toDTO(userDomain)).thenReturn(responseDTO);

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectNext(responseDTO).verifyComplete();

    Mockito.verify(userRepository).save(userDomain);
  }

  @Test
  void shouldReturnErrorWhenEmailAlreadyExists() {
    Mockito.when(userMapper.toDomainCommand(command)).thenReturn(userDomain);
    Mockito.when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(true));

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectError(DuplicatedInfoException.class).verify();

    Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
  }
}
