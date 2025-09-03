package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.DomainConstants;
import co.com.crediya.auth.model.user.exceptions.*;

public record IdNumber(String value) {
  private static final int MIN_VALUE = DomainConstants.USER_MIN_ID_NUMBER_LENGTH;
  private static final int MAX_VALUE = DomainConstants.USER_MAX_ID_NUMBER_LENGTH;

  public IdNumber {
    if (value == null || value.isBlank()) {
      throw new MissingValueOnRequiredFieldException(
          Fields.ID_NUMBER.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    if (value.trim().length() < MIN_VALUE || value.trim().length() > MAX_VALUE) {
      throw new IllegalValueForArgumentException(
          Fields.ID_NUMBER.getName(),
          TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_VALUE, MAX_VALUE));
    }
  }
}
