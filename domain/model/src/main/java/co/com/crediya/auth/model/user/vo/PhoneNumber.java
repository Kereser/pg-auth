package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.DomainConstants;
import co.com.crediya.auth.model.user.exceptions.Fields;
import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.TemplateErrors;

public record PhoneNumber(String value) {
  private static final int MIN_LENGTH = DomainConstants.USER_PHONE_MIN_LENGTH;
  private static final int MAX_LENGTH = DomainConstants.USER_PHONE_MAX_LENGTH;

  public PhoneNumber {
    if (value != null
        && (value.trim().length() > MAX_LENGTH || value.trim().length() < MIN_LENGTH)) {
      throw new IllegalValueForArgumentException(
          Fields.PHONE_NUMBER.getName(),
          TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_LENGTH, MAX_LENGTH));
    }
  }
}
