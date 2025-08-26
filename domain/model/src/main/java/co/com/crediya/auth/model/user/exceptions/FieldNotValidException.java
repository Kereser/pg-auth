package co.com.crediya.auth.model.user.exceptions;

public class FieldNotValidException extends BusinessException {
  private static final String BASE_MSG = "Field error conditions.";

  public FieldNotValidException(String field, String reason) {
    super(BASE_MSG, field, reason);
  }
}
