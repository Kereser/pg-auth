package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.*;

public record FirstName(String value) {
  private static final String FIELD = "firstName";
  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 30;

  public FirstName {
    if (value == null || value.isBlank()) {
      throw new MissingValueOnRequiredFieldException(
          Fields.FIRST_NAME.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    if (value.trim().length() > MAX_LENGTH) {
      throw new IllegalValueForArgumentException(
          Fields.FIRST_NAME.getName(),
          TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_LENGTH, MAX_LENGTH));
    }
  }
}
