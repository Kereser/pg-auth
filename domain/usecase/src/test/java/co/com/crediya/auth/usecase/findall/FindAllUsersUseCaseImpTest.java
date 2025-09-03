package co.com.crediya.auth.usecase.findall;

import static co.com.crediya.auth.model.DomainConstants.USER_MAX_SALARY;
import static co.com.crediya.auth.usecase.DataUtils.*;
import static co.com.crediya.auth.usecase.DataUtils.randomBigDecimal;
import static co.com.crediya.auth.usecase.DataUtils.randomIdNumber;
import static co.com.crediya.auth.usecase.DataUtils.randomIdType;
import static co.com.crediya.auth.usecase.DataUtils.randomName;
import static org.mockito.Mockito.*;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.*;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FindAllUsersUseCaseImpTest {

  static {
    BlockHound.install();
  }

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;

  @InjectMocks private FindAllUsersUseCaseImp findAllUsersUseCaseImp;

  private FindAllUsersFiltersCommand command;
  private UserResponseDTO responseDTO1;
  private UserResponseDTO responseDTO2;
  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    command = FindAllUsersFiltersCommand.builder().userIds(Set.of(UUID.randomUUID())).build();

    user1 =
        User.builder()
            .id(UUID.randomUUID())
            .firstName(new FirstName(randomName()))
            .baseSalary(new UserSalary(randomBigDecimal(USER_MAX_SALARY)))
            .email(new UserEmail(randomEmail()))
            .idType(IdType.fromCode(randomIdType()))
            .idNumber(new IdNumber(randomIdNumber()))
            .build();

    user2 =
        User.builder()
            .id(UUID.randomUUID())
            .firstName(new FirstName(randomName()))
            .baseSalary(new UserSalary(randomBigDecimal(USER_MAX_SALARY)))
            .email(new UserEmail(randomEmail()))
            .idType(IdType.fromCode(randomIdType()))
            .idNumber(new IdNumber(randomIdNumber()))
            .build();

    responseDTO1 =
        new UserResponseDTO(
            user1.getId(),
            user1.getEmail().value(),
            user1.getBaseSalary().value(),
            user1.getFirstName().value(),
            user1.getIdType().name(),
            user1.getIdNumber().value());

    responseDTO2 =
        new UserResponseDTO(
            user2.getId(),
            user2.getEmail().value(),
            user2.getBaseSalary().value(),
            user2.getFirstName().value(),
            user2.getIdType().name(),
            user2.getIdNumber().value());
  }

  @Test
  void shouldReturnFluxOfUsersWhenFound() {
    when(userRepository.findAllFiltered(command)).thenReturn(Flux.just(user1, user2));

    when(userMapper.toDTOResponse(user1)).thenReturn(responseDTO1);
    when(userMapper.toDTOResponse(user2)).thenReturn(responseDTO2);

    Flux<UserResponseDTO> resultFlux = findAllUsersUseCaseImp.execute(command);

    StepVerifier.create(resultFlux)
        .expectNext(responseDTO1)
        .expectNext(responseDTO2)
        .verifyComplete();

    verify(userRepository).findAllFiltered(command);
  }

  @Test
  void shouldReturnEmptyFluxWhenNoUsersFound() {
    when(userRepository.findAllFiltered(command)).thenReturn(Flux.empty());

    Flux<UserResponseDTO> resultFlux = findAllUsersUseCaseImp.execute(command);

    StepVerifier.create(resultFlux).expectNextCount(0).verifyComplete();

    verify(userMapper, never()).toDTOResponse(any(User.class));
  }
}
