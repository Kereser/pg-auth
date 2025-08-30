package co.com.crediya.auth.model.user.mapper;

import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;

public interface UserMapper {
  User toDomainFromCommand(CreateUserCommand command);

  UserResponseDTO toDTOResponse(User user);
}
