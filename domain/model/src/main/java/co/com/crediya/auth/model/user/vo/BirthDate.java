package co.com.crediya.auth.model.user.vo;

import java.time.LocalDate;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;

public record BirthDate(LocalDate value) {
  private static final String FIELD = "birthDate";
  private static final String AGE_EXCEPTION = "User must be between 18 and 100 years old.";
  private static final String NOT_EMPTY = "Must not be empty";

  public BirthDate {
    if (value == null) {
      throw new MissingValueOnRequiredFieldException(FIELD, NOT_EMPTY);
    }

    LocalDate today = LocalDate.now();
    LocalDate minAllowed = today.minusYears(100);
    LocalDate maxAllowed = today.minusYears(18);

    if (value.isBefore(minAllowed) || value.isAfter(maxAllowed)) {
      throw new IllegalValueForArgumentException(FIELD, AGE_EXCEPTION);
    }
  }
}
