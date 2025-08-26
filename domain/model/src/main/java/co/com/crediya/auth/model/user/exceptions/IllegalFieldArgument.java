package co.com.crediya.auth.model.user.exceptions;

public class IllegalFieldArgument extends BusinessException {
  private static final String BASE_MSG = "Value cannot be converter to a field valid value";

  public IllegalFieldArgument(String attribute, String reason) {
    super(BASE_MSG, attribute, reason);
  }
}
