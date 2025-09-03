package co.com.crediya.auth.model.user.vo;

import java.math.BigDecimal;

import co.com.crediya.auth.model.DomainConstants;
import co.com.crediya.auth.model.user.exceptions.*;

public record UserSalary(BigDecimal value) {
  private static final BigDecimal MIN_VALUE = DomainConstants.USER_MIN_SALARY;
  private static final BigDecimal MAX_VALUE = DomainConstants.USER_MAX_SALARY;

  public UserSalary {
    if (value == null) {
      throw new MissingValueOnRequiredFieldException(
          Fields.SALARY.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    if (value.compareTo(MAX_VALUE) > 0 || value.compareTo(MIN_VALUE) < 0) {
      throw new IllegalValueForArgumentException(
          Fields.SALARY.getName(), TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_VALUE, MAX_VALUE));
    }
  }
}
