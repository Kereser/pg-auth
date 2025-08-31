package co.com.crediya.auth.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes")
public class Routes {

  private Variables variables;
  private Paths paths;

  @Getter
  @Setter
  public static class Variables {
    private String idNumber;
  }

  @Getter
  @Setter
  public static class Paths {
    private String base;
    private String users;
    private String login;
    private String userByIdNumber;
  }
}
