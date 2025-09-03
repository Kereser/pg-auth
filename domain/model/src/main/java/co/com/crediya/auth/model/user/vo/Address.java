package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.DomainConstants;
import co.com.crediya.auth.model.user.exceptions.*;

public record Address(String value) {
  private static final int MIN_LENGTH = DomainConstants.USER_ADDRESS_MIN_LENGTH;
  private static final int MAX_LENGTH = DomainConstants.USER_ADDRESS_MAX_LENGTH;

  public Address {
    if (value != null) {
      if (value.isBlank()) {
        throw new MissingValueOnRequiredFieldException(
            Fields.ADDRESS.getName(), PlainErrors.NOT_EMPY.getMsg());
      }

      if (value.trim().length() > MAX_LENGTH || value.trim().length() < MIN_LENGTH) {
        throw new IllegalValueForArgumentException(
            Fields.ADDRESS.getName(),
            TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_LENGTH, MAX_LENGTH));
      }
    }
  }
}
