package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;

public record Address(String value) {
  private static final String FIELD = "address";
  private static final int MIN_LENGTH = 5;
  private static final int MAX_LENGTH = 80;
  private static final String LENGTH_EXCEPTION = "Address length must be between %d and %d";
  private static final String MUST_HAVE_VALUE = "Address must have a value if its present.";

  public Address {
    if (value != null) {
      if (value.isBlank()) {
        throw new MissingValueOnRequiredFieldException(FIELD, MUST_HAVE_VALUE);
      }

      if (value.trim().length() > MAX_LENGTH || value.trim().length() < MIN_LENGTH) {
        throw new IllegalValueForArgumentException(
            FIELD, String.format(LENGTH_EXCEPTION, MIN_LENGTH, MAX_LENGTH));
      }
    }
  }
}
