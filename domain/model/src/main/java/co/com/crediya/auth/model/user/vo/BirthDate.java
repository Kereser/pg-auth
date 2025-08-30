package co.com.crediya.auth.model.user.vo;

import java.time.LocalDate;

import co.com.crediya.auth.model.user.exceptions.*;

public record BirthDate(LocalDate value) {
  private static final int MIN_AGE = 18;
  private static final int MAX_AGE = 99;

  public BirthDate {
    if (value == null) {
      throw new MissingValueOnRequiredFieldException(
          Fields.BIRTH_DATE.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    LocalDate today = LocalDate.now();
    LocalDate minAllowed = today.minusYears(MAX_AGE);
    LocalDate maxAllowed = today.minusYears(MIN_AGE);

    if (value.isBefore(minAllowed) || value.isAfter(maxAllowed)) {
      throw new IllegalValueForArgumentException(
          Fields.BIRTH_DATE.getName(), TemplateErrors.LENGTH_BOUNDARIES.buildMsg(MIN_AGE, MAX_AGE));
    }
  }
}
