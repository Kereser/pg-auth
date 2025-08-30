package co.com.crediya.auth.usecase.findbyidnumber;

import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.vo.IdNumber;
import reactor.core.publisher.Mono;

public interface FindByIdNumberUseCase {
  Mono<UserResponseDTO> execute(IdNumber number);
}
