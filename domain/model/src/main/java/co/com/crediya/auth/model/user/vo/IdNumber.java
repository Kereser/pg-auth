package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.FieldNotValidException;

public record IdNumber(String value) {
  private static final String FIELD = "idNumber";
  private static final int MIN_VALUE = 5;
  private static final int MAX_VALUE = 20;
  private static final String VALUE_LENGTH = "idNumber values must be between %d and %d.";
  private static final String NOT_EMPTY = "Must not be empty";

  public IdNumber {
    if (value == null || value.isBlank()) {
      throw new FieldNotValidException(FIELD, NOT_EMPTY);
    }

    if (value.trim().length() < MIN_VALUE || value.trim().length() > MAX_VALUE) {
      throw new FieldNotValidException(FIELD, String.format(VALUE_LENGTH, MIN_VALUE, MAX_VALUE));
    }
  }
}
