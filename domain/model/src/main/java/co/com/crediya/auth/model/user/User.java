package co.com.crediya.auth.model.user;

import java.util.UUID;

import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.model.user.vo.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
  private UUID id;

  private UserEmail email;
  private String password;

  private FirstName firstName;
  private LastName lastName;
  private BirthDate birthDate;

  private Address address;
  private UserSalary baseSalary;
  private PhoneNumber phoneNumber;

  private IdType idType;
  private IdNumber idNumber;

  private Role role;
}
