package co.com.crediya.auth.api.exceptions;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import co.com.crediya.auth.model.user.exceptions.*;

@Configuration
public class ExceptionConfig {

  @Bean
  public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
    return Map.of(
        MissingRequiredParam.class,
        HttpStatus.BAD_REQUEST,
        MissingValueOnRequiredFieldException.class,
        HttpStatus.BAD_REQUEST,
        DuplicatedInfoException.class,
        HttpStatus.CONFLICT,
        IllegalValueForArgumentException.class,
        HttpStatus.BAD_REQUEST,
        EntityNotFoundException.class,
        HttpStatus.NOT_FOUND,
        MissingRequiredBodyException.class,
        HttpStatus.BAD_REQUEST);
  }

  @Bean
  public WebProperties.Resources webResources() {
    return new WebProperties.Resources();
  }

  @Bean
  public HttpStatus defaultStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
