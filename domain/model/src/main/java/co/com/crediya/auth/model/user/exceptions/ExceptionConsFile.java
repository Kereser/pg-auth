package co.com.crediya.auth.model.user.exceptions;

public abstract class ExceptionConsFile {
  private ExceptionConsFile() {}

  public static final String EMAIL_ALREADY_IN_USER = "Email is already in use";
  public static final String EMAIL_FIELD = "email";
  public static final String NOT_EMPY = "Must not be empty or blank";
}
