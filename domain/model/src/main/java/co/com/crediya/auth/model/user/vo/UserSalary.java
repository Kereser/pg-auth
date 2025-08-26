package co.com.crediya.auth.model.user.vo;

import java.math.BigDecimal;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;

public record UserSalary(BigDecimal value) {
  private static final String FIELD = "baseSalary";
  private static final BigDecimal MIN_VALUE = BigDecimal.ZERO;
  private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(15_000_000);
  private static final String MINIMUM_VAL_EXCEPTION = "Minimum salary must be at least: %s";
  private static final String MAX_VAL_EXCEPTION = "Max salary must be: %s";

  public UserSalary {
    if (value == null || value.compareTo(MIN_VALUE) < 0) {
      throw new MissingValueOnRequiredFieldException(
          FIELD, String.format(MINIMUM_VAL_EXCEPTION, MIN_VALUE.toPlainString()));
    }

    if (value.compareTo(MAX_VALUE) > 0) {
      throw new IllegalValueForArgumentException(
          FIELD, String.format(MAX_VAL_EXCEPTION, MAX_VALUE.toPlainString()));
    }
  }
}
