package co.com.crediya.auth.api.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.reactive.function.server.ServerRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class FindAllUsersFiltersDTORequest {
  private static final String USER_IDS = "ids";

  Set<UUID> userIds;

  public static FindAllUsersFiltersDTORequest from(ServerRequest req) {
    FindAllUsersFiltersDTORequestBuilder builder = FindAllUsersFiltersDTORequest.builder();

    List<String> rawIds = req.queryParams().getOrDefault(USER_IDS, List.of());

    Set<UUID> ids =
        rawIds.stream()
            .flatMap(s -> Stream.of(s.split(",")))
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .map(UUID::fromString)
            .collect(Collectors.toSet());

    if (!ids.isEmpty()) {
      builder.userIds(ids);
    }

    return builder.build();
  }
}
