package co.com.crediya.auth.api.helper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.com.crediya.auth.api.dto.CreateUserDTORequest;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRestMapper {
  CreateUserCommand toCommand(CreateUserDTORequest dto);
}
