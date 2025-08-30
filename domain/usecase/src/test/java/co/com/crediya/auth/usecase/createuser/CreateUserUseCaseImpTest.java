package co.com.crediya.auth.usecase.createuser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.DuplicatedInfoException;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.FirstName;
import co.com.crediya.auth.model.user.vo.IdNumber;
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
            .idNumber(new IdNumber("12345"))
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
  void shouldCreateUserWhenDataIsUnique() {
    when(userMapper.toDomainFromCommand(command)).thenReturn(userDomain);

    when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(false));
    when(userRepository.findByIdNumber(userDomain.getIdNumber())).thenReturn(Mono.empty());

    when(userRepository.save(userDomain)).thenReturn(Mono.just(userDomain));
    when(userMapper.toDTOResponse(userDomain)).thenReturn(responseDTO);

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectNext(responseDTO).verifyComplete();

    verify(userRepository).save(userDomain);
  }

  @Test
  void shouldReturnErrorWhenEmailExists() {
    when(userMapper.toDomainFromCommand(command)).thenReturn(userDomain);

    when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(true));
    when(userRepository.findByIdNumber(userDomain.getIdNumber())).thenReturn(Mono.empty());

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectError(DuplicatedInfoException.class).verify();

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void shouldReturnErrorWhenIdNumberExists() {
    when(userMapper.toDomainFromCommand(command)).thenReturn(userDomain);

    when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(false));
    when(userRepository.findByIdNumber(userDomain.getIdNumber())).thenReturn(Mono.just(userDomain));

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectError(DuplicatedInfoException.class).verify();

    verify(userRepository, never()).save(any(User.class));
  }
}
