package co.com.crediya.auth.usecase.findbyidnumber;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.exceptions.EntityNotFoundException;
import co.com.crediya.auth.model.user.gateways.UserRepository;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.FirstName;
import co.com.crediya.auth.model.user.vo.IdNumber;
import co.com.crediya.auth.model.user.vo.UserEmail;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FindByIdNumberUseCaseImpTest {

  static {
    BlockHound.install();
  }

  @Mock private UserRepository userRepository;

  @Mock private UserMapper userMapper;

  @InjectMocks private FindByIdNumberUseCaseImp findByIdNumberUseCase;

  private IdNumber idNumber;
  private User userDomain;
  private UserResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    idNumber = new IdNumber("12345");

    userDomain =
        User.builder()
            .id(UUID.randomUUID())
            .idNumber(idNumber)
            .email(new UserEmail("test@test.com"))
            .firstName(new FirstName("Jane"))
            .build();

    responseDTO = new UserResponseDTO(userDomain.getId(), "test@test.com", "Jane", "CC", "12345");
  }

  @Test
  void shouldReturnUserWhenIdNumberExists() {
    Mockito.when(userRepository.findByIdNumber(idNumber)).thenReturn(Mono.just(userDomain));
    Mockito.when(userMapper.toDTOResponse(userDomain)).thenReturn(responseDTO);

    Mono<UserResponseDTO> resultMono = findByIdNumberUseCase.execute(idNumber);

    StepVerifier.create(resultMono).expectNext(responseDTO).verifyComplete();
  }

  @Test
  void shouldReturnErrorWhenIdNumberDoesNotExist() {
    Mockito.when(userRepository.findByIdNumber(idNumber)).thenReturn(Mono.empty());

    Mono<UserResponseDTO> resultMono = findByIdNumberUseCase.execute(idNumber);

    StepVerifier.create(resultMono).expectError(EntityNotFoundException.class).verify();

    Mockito.verify(userMapper, Mockito.never()).toDTOResponse(Mockito.any(User.class));
  }
}
