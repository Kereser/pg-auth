package co.com.crediya.auth.model.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlainErrors {
  NOT_EMPY("Must not be empty nor blank");

  private final String msg;
}
