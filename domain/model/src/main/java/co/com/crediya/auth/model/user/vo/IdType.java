package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.ExceptionConsFile;
import co.com.crediya.auth.model.user.exceptions.FieldNotValidException;

public record IdType(String value) {
  private static final String FIELD = "idType";

  public IdType {
    if (value == null || value.isBlank()) {
      throw new FieldNotValidException(FIELD, ExceptionConsFile.NOT_EMPY);
    }
  }
}
