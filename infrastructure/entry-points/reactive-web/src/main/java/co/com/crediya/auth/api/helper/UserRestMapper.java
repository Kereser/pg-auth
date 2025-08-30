package co.com.crediya.auth.api.helper;

import org.mapstruct.Mapper;

import co.com.crediya.auth.api.dto.CreateUserDTORequest;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
  CreateUserCommand toCommand(CreateUserDTORequest dto);
}
