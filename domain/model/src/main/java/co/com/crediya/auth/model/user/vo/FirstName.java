package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;

public record FirstName(String value) {
  private static final String FIELD = "firstName";
  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 30;
  private static final String MINIMUM_LENGTH_EXCEPTION = "Minimum length must be at least: %d";
  private static final String MAX_LENGTH_EXCEPTION = "Max length must be: %d";

  public FirstName {
    if (value == null || value.isBlank()) {
      throw new MissingValueOnRequiredFieldException(
          FIELD, String.format(MINIMUM_LENGTH_EXCEPTION, MIN_LENGTH));
    }

    if (value.trim().length() > MAX_LENGTH) {
      throw new IllegalValueForArgumentException(
          FIELD, String.format(MAX_LENGTH_EXCEPTION, MAX_LENGTH));
    }
  }
}
