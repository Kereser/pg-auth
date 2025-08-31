package co.com.crediya.auth.r2dbc.user.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.*;
import co.com.crediya.auth.model.user.vo.IdType;
import co.com.crediya.auth.r2dbc.user.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapperStandard extends UserMapper {
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "firstName", source = "firstName.value")
  @Mapping(target = "lastName", source = "lastName.value")
  @Mapping(target = "baseSalary", source = "baseSalary.value")
  @Mapping(target = "idNumber", source = "idNumber.value")
  @Mapping(target = "birthDate", source = "birthDate.value")
  @Mapping(target = "phoneNumber", source = "phoneNumber.value")
  @Mapping(target = "address", source = "address.value")
  @Mapping(target = "roleId", source = "role.id")
  UserEntity toData(User domain);

  @Mapping(target = "role", expression = "java(toUserRole(entity.roleId()))")
  User toEntity(UserEntity entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", ignore = true)
  User toDomainFromCommand(CreateUserCommand command);

  @Override
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "firstName", source = "firstName.value")
  @Mapping(target = "idNumber", source = "idNumber.value")
  @Mapping(target = "idType", source = "idType.code")
  UserResponseDTO toDTOResponse(User user);

  default Role toUserRole(UUID id) {
    return new Role().toBuilder().id(id).build();
  }

  default UserEmail toUserEmail(String email) {
    return new UserEmail(email);
  }

  default FirstName toFirstName(String firstName) {
    return new FirstName(firstName);
  }

  default LastName toLastName(String lastName) {
    return new LastName(lastName);
  }

  default BirthDate toBirthDate(LocalDate date) {
    return new BirthDate(date);
  }

  default Address toAddress(String address) {
    return new Address(address);
  }

  default UserSalary toUserSalary(BigDecimal salary) {
    return new UserSalary(salary);
  }

  default PhoneNumber toPhoneNumber(String phoneNumber) {
    return new PhoneNumber(phoneNumber);
  }

  default IdType toIdType(String idType) {
    return IdType.fromCode(idType);
  }

  default IdNumber toIdNumber(String idNumber) {
    return new IdNumber(idNumber);
  }
}
