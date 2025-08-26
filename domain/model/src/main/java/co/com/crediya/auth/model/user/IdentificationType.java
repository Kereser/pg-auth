package co.com.crediya.auth.model.user;

import java.util.HashMap;
import java.util.Map;

import co.com.crediya.auth.model.user.exceptions.ExceptionConsFile;
import co.com.crediya.auth.model.user.exceptions.FieldNotValidException;
import co.com.crediya.auth.model.user.exceptions.IllegalFieldArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IdentificationType {
  CC("CC"),
  PASSPORT("PASSPORT");

  private final String code;
  private static final String ENTITY = "idType";
  private static final String NOT_VALID_VALUE = "%s is not a valid value for %s";

  private static final Map<String, IdentificationType> BY_CODE = new HashMap<>();

  static {
    for (IdentificationType e : values()) {
      BY_CODE.put(e.code, e);
    }
  }

  public static IdentificationType fromCode(String code) {
    if (code == null) {
      throw new FieldNotValidException(ENTITY, ExceptionConsFile.NOT_EMPY);
    }

    code = code.toUpperCase();
    IdentificationType type = BY_CODE.get(code);

    if (type == null) {
      throw new IllegalFieldArgument(ENTITY, String.format(NOT_VALID_VALUE, code, ENTITY));
    }

    return type;
  }
}
