package co.com.crediya.auth.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class UserPath {
  private String base;
  private String users;
}
