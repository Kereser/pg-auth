package co.com.crediya.auth.model;

import java.math.BigDecimal;

public class DomainConstants {
  private DomainConstants() {}

  public static final BigDecimal USER_MAX_SALARY = BigDecimal.valueOf(15_000_000);
  public static final BigDecimal USER_MIN_SALARY = BigDecimal.ZERO;

  public static final int USER_ADDRESS_MIN_LENGTH = 5;
  public static final int USER_ADDRESS_MAX_LENGTH = 80;

  public static final int USER_PHONE_MIN_LENGTH = 10;
  public static final int USER_PHONE_MAX_LENGTH = 13;

  public static final int USER_BIRTH_DATE_MIN = 18;
  public static final int USER_BIRTH_DATE_MAX = 99;

  public static final int USER_MIN_FIRST_NAME_LENGTH = 3;
  public static final int USER_MAX_FIRST_NAME_LENGTH = 30;

  public static final int USER_MIN_LAST_NAME_LENGTH = 3;
  public static final int USER_MAX_LAST_NAME_LENGTH = 30;

  public static final int USER_MIN_ID_NUMBER_LENGTH = 5;
  public static final int USER_MAX_ID_NUMBER_LENGTH = 20;

  public static final int USER_EMAIL_MAX_LENGTH = 65;
  public static final String USER_EMAIL_REGEX = "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
}
