package co.com.crediya.auth.model.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
  CLIENT("CLIENT"),
  MANAGER("MANAGER"),
  ADMIN("ADMIN");

  private final String name;
}
