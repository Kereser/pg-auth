package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.DomainConstants;
import co.com.crediya.auth.model.user.exceptions.*;

public record LastName(String value) {
  private static final int MIN_LENGTH = DomainConstants.USER_MIN_LAST_NAME_LENGTH;
  private static final int MAX_LENGTH = DomainConstants.USER_MAX_LAST_NAME_LENGTH;

  public LastName {
    if (value == null || value.isBlank()) {
      throw new MissingValueOnRequiredFieldException(
          Fields.LAST_NAME.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    if (value.trim().length() > MAX_LENGTH) {
      throw new IllegalValueForArgumentException(
          Fields.LAST_NAME.getName(),
          TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_LENGTH, MAX_LENGTH));
    }
  }
}
