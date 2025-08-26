package co.com.crediya.auth.model.user.exceptions;

public class MissingRequiredParam extends BusinessException {
  private static final String BASE_MSG = "Required param is not available";

  public MissingRequiredParam(String missingParam, String reason) {
    super(BASE_MSG, missingParam, reason);
  }
}
