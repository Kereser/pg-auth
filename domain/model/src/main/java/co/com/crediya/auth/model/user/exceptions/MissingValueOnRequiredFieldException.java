package co.com.crediya.auth.model.user.exceptions;

public class MissingValueOnRequiredFieldException extends BusinessException {
  private static final String BASE_MSG = "Missing required value on field";

  public MissingValueOnRequiredFieldException(String attribute, String reason) {
    super(BASE_MSG, attribute, reason);
  }
}
