package co.com.crediya.auth.model.user;

import java.util.HashMap;
import java.util.Map;

import co.com.crediya.auth.model.user.exceptions.ExceptionConsFile;
import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IdType {
  CC("CC"),
  PASSPORT("PASSPORT");

  private final String code;
  private static final String ENTITY = "idType";
  private static final String NOT_VALID_VALUE = "%s is not a valid value for %s";

  private static final Map<String, IdType> BY_CODE = new HashMap<>();

  static {
    for (IdType e : values()) {
      BY_CODE.put(e.code, e);
    }
  }

  public static IdType fromCode(String code) {
    if (code == null) {
      throw new MissingValueOnRequiredFieldException(ENTITY, ExceptionConsFile.NOT_EMPY);
    }

    code = code.toUpperCase();
    IdType type = BY_CODE.get(code);

    if (type == null) {
      throw new IllegalValueForArgumentException(
          ENTITY, String.format(NOT_VALID_VALUE, code, ENTITY));
    }

    return type;
  }
}
