package co.com.crediya.auth.usecase.createuser;

import static co.com.crediya.auth.model.DomainConstants.USER_MAX_SALARY;
import static co.com.crediya.auth.usecase.DataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.helper.PasswordEncoder;
import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.model.role.RoleName;
import co.com.crediya.auth.model.role.gateways.RoleRepository;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.DuplicatedInfoException;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.FirstName;
import co.com.crediya.auth.model.user.vo.IdNumber;
import co.com.crediya.auth.model.user.vo.UserEmail;
import co.com.crediya.auth.model.user.vo.UserSalary;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImpTest {
  static {
    BlockHound.install();
  }

  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private UserMapper userMapper;
  @InjectMocks private CreateUserUseCaseImp createUserUseCase;

  @Captor private ArgumentCaptor<User> userArgumentCaptor;

  private CreateUserCommand command;
  private User userDomain;
  private UserResponseDTO responseDTO;
  private Role clientRole;

  @BeforeEach
  void setUp() {
    command =
        new CreateUserCommand(
            randomEmail(),
            randomPassword(10),
            randomName(),
            randomName(),
            now().plusYears(20),
            randomAddress(),
            randomPhone(),
            randomBigDecimal(),
            randomIdType(),
            randomIdNumber());

    userDomain =
        User.builder()
            .id(UUID.randomUUID())
            .email(new UserEmail(randomEmail()))
            .baseSalary(new UserSalary(randomBigDecimal(USER_MAX_SALARY)))
            .password(randomPassword(10))
            .firstName(new FirstName(randomName()))
            .idNumber(new IdNumber(randomIdNumber()))
            .role(clientRole)
            .build();

    responseDTO =
        new UserResponseDTO(
            userDomain.getId(),
            userDomain.getEmail().value(),
            userDomain.getBaseSalary().value(),
            userDomain.getFirstName().value(),
            randomIdType(),
            randomIdNumber());

    clientRole = new Role(UUID.randomUUID(), RoleName.CLIENT, randomEmail());
  }

  @Test
  void shouldCreateUserWhenDataIsUnique() {
    when(userMapper.toDomainFromCommand(command)).thenReturn(userDomain);

    when(userRepository.existsByEmail(userDomain.getEmail())).thenReturn(Mono.just(false));
    when(userRepository.findByIdNumber(userDomain.getIdNumber())).thenReturn(Mono.empty());
    String mockedPass = randomPassword(30);
    when(passwordEncoder.encode(userDomain.getPassword())).thenReturn(mockedPass);
    when(roleRepository.findClientRole()).thenReturn(Mono.just(clientRole));

    when(userRepository.save(any(User.class)))
        .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    when(userMapper.toDTOResponse(any(User.class))).thenReturn(responseDTO);

    Mono<UserResponseDTO> resultMono = createUserUseCase.execute(command);

    StepVerifier.create(resultMono).expectNext(responseDTO).verifyComplete();

    verify(userRepository).save(userArgumentCaptor.capture());
    User userSentToSave = userArgumentCaptor.getValue();

    assertThat(userSentToSave.getPassword()).isEqualTo(mockedPass);
    assertThat(userSentToSave.getRole()).isEqualTo(clientRole);
    assertThat(userSentToSave.getEmail()).isEqualTo(userDomain.getEmail());
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
