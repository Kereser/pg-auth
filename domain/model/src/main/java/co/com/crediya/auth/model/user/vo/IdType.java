package co.com.crediya.auth.model.user.vo;

import java.util.HashMap;
import java.util.Map;

import co.com.crediya.auth.model.user.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IdType {
  CC("CC"),
  PASSPORT("PASSPORT");

  private final String code;

  private static final Map<String, IdType> BY_CODE = new HashMap<>();

  static {
    for (IdType e : values()) {
      BY_CODE.put(e.code, e);
    }
  }

  public static IdType fromCode(String code) {
    if (code == null) {
      throw new MissingValueOnRequiredFieldException(
          Fields.ID_TYPE.getName(), PlainErrors.NOT_EMPY.getMsg());
    }

    code = code.toUpperCase();
    IdType type = BY_CODE.get(code);

    if (type == null) {
      throw new IllegalValueForArgumentException(
          Fields.ID_TYPE.getName(),
          TemplateErrors.X_NOT_VALID_FORMAT_FOR_Y.buildMsg(code, Fields.ID_TYPE.getName()));
    }

    return type;
  }
}
