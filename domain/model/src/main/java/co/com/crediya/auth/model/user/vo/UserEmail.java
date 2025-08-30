package co.com.crediya.auth.model.user.vo;

import java.util.regex.Pattern;

import co.com.crediya.auth.model.user.exceptions.*;

public record UserEmail(String value) {
  private static final int MAX_LENGTH = 65;
  private static final String regex = "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$";

  public UserEmail {
    if (value == null || value.isEmpty()) {
      throw new MissingValueOnRequiredFieldException(
          Fields.EMAIL.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    if (value.trim().length() > MAX_LENGTH) {
      throw new IllegalValueForArgumentException(
          Fields.EMAIL.getName(), TemplateErrors.MAX_LENGTH.buildMsg(MAX_LENGTH));
    }

    if (!Pattern.matches(regex, value)) {
      throw new IllegalValueForArgumentException(
          Fields.EMAIL.getName(),
          TemplateErrors.X_NOT_VALID_FORMAT_FOR_Y.buildMsg(value, Fields.EMAIL.getName()));
    }
  }
}
