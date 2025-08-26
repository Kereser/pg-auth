package co.com.crediya.auth.model.user.exceptions;

public class IllegalValueForArgumentException extends BusinessException {
  private static final String BASE_MSG = "Value is not valid for given field";

  public IllegalValueForArgumentException(String attribute, String reason) {
    super(BASE_MSG, attribute, reason);
  }
}
