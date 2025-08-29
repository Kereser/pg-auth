package co.com.crediya.auth.model.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Fields {
  EMAIL("Email"),
  ID_NUMBER("Id number"),
  BIRTH_DATE("Birth date"),
  FIRST_NAME("First name"),
  LAST_NAME("Last name"),
  ID_TYPE("Id type"),
  PHONE_NUMBER("Phone number"),
  SALARY("Salary"),
  ADDRESS("Address");

  private final String name;
}
