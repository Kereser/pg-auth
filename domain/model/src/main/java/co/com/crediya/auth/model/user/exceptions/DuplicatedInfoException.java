package co.com.crediya.auth.model.user.exceptions;

public class DuplicatedInfoException extends BusinessException {
  private static final String BASE_MSG = "Duplicated information found";

  public DuplicatedInfoException(String attribute, String reason) {
    super(BASE_MSG, attribute, reason);
  }
}
