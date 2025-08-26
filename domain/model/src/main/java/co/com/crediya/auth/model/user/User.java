package co.com.crediya.auth.model.user;

import java.util.UUID;

import co.com.crediya.auth.model.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
  private UUID id;

  private UserEmail email;
  private FirstName firstName;
  private LastName lastName;
  private BirthDate birthDate;

  private Address address;
  private UserSalary baseSalary;
  private PhoneNumber phoneNumber;

  private IdentificationType idType;
  private IdNumber idNumber;
}
