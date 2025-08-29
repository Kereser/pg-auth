package co.com.crediya.auth.model.user.vo;

import co.com.crediya.auth.model.user.exceptions.Fields;
import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.TemplateErrors;

public record PhoneNumber(String value) {
  private static final int MAX_LENGTH = 13;
  private static final int MIN_LENGTH = 10;

  public PhoneNumber {
    if (value != null
        && (value.trim().length() > MAX_LENGTH || value.trim().length() < MIN_LENGTH)) {
      throw new IllegalValueForArgumentException(
          Fields.PHONE_NUMBER.getName(),
          TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_LENGTH, MAX_LENGTH));
    }
  }
}
