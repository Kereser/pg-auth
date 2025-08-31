package co.com.crediya.auth.model;

public abstract class RegexPatterns {
  private RegexPatterns() {}

  public static final String PASSWORD =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[^a-zA-Z0-9]).{7,}$";
}
