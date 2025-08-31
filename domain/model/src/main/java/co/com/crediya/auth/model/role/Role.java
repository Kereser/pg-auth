package co.com.crediya.auth.model.role;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Role {
  private UUID id;
  private RoleName name;
  private String description;
}
