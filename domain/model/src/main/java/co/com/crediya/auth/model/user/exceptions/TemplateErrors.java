package co.com.crediya.auth.model.user.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateErrors {
  X_ALREADY_IN_USER("%s is already in use"),
  X_NOT_FOUND_FOR_Y("%s was not found for %s"),
  X_NOT_VALID_FORMAT_FOR_Y("Value %s does not meet format condition for %s field"),
  LENGTH_BOUNDARIES("Value must be between %s and %s"),
  MAX_LENGTH("Max length as a value is: %d");

  private final String msg;

  public String buildMsg(Object... args) {
    return String.format(msg, args);
  }
}
