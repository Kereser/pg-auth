package co.com.crediya.auth.r2dbc.helper;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.crediya.auth.model.user.IdType;
import co.com.crediya.auth.model.user.User;
import co.com.crediya.auth.model.user.dto.CreateUserCommand;
import co.com.crediya.auth.model.user.dto.UserResponseDTO;
import co.com.crediya.auth.model.user.mapper.UserMapper;
import co.com.crediya.auth.model.user.vo.*;
import co.com.crediya.auth.r2dbc.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapperStandard extends UserMapper {
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "firstName", source = "firstName.value")
  @Mapping(target = "lastName", source = "lastName.value")
  @Mapping(target = "baseSalary", source = "baseSalary.value")
  @Mapping(target = "idNumber", source = "idNumber.value")
  @Mapping(target = "birthDate", source = "birthDate.value")
  @Mapping(target = "phoneNumber", source = "phoneNumber.value")
  @Mapping(target = "address", source = "address.value")
  UserEntity toEntity(User domain);

  User toDomain(UserEntity entity);

  @Override
  @Mapping(target = "id", ignore = true)
  User toDomainCommand(CreateUserCommand command);

  @Override
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "firstName", source = "firstName.value")
  @Mapping(target = "idNumber", source = "idNumber.value")
  @Mapping(target = "idType", source = "idType.code")
  UserResponseDTO toDTO(User user);

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
