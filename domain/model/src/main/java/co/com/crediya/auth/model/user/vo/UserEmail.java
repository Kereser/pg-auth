package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.ExceptionConsFile;
import co.com.crediya.auth.model.user.exceptions.FieldNotValidException;

public record UserEmail(String value) {
  private static final String ENTITY = "email";
  private static final int MAX_LENGTH = 55;
  private static final String MAX_LENGTH_MSG = "Value exceed %d characters.";

  public UserEmail {
    if (value == null || value.isEmpty()) {
      throw new FieldNotValidException(ENTITY, ExceptionConsFile.NOT_EMPY);
    }

    if (value.trim().length() > MAX_LENGTH) {
      throw new FieldNotValidException(ENTITY, String.format(MAX_LENGTH_MSG, MAX_LENGTH));
    }
  }
}
