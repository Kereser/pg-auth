package co.com.crediya.auth.api.helper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.com.crediya.auth.api.dto.CreateUserDTORequest;
import co.com.crediya.auth.api.dto.FindAllUsersFiltersDTORequest;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRestMapper {
  CreateUserCommand toCommand(CreateUserDTORequest dto);

  FindAllUsersFiltersCommand toCommand(FindAllUsersFiltersDTORequest dto);
}
