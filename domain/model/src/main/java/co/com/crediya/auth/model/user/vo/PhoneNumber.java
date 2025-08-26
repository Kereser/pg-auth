package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;

public record PhoneNumber(String value) {
  private static final String FIELD = "phoneNumber";
  private static final int MAX_LENGTH = 13;
  private static final int MIN_LENGTH = 10;
  private static final String LENGTH_EXCEPTION = "Phone number must be between: %d and %d";

  public PhoneNumber {
    if (value != null
        && (value.trim().length() > MAX_LENGTH || value.trim().length() < MIN_LENGTH)) {
      throw new IllegalValueForArgumentException(
          FIELD, String.format(LENGTH_EXCEPTION, MIN_LENGTH, MAX_LENGTH));
    }
  }
}
