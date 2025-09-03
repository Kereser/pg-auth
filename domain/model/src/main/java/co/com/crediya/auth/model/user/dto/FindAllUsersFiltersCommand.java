package co.com.crediya.auth.model.user.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
public class FindAllUsersFiltersCommand {
  Set<UUID> userIds;
}
