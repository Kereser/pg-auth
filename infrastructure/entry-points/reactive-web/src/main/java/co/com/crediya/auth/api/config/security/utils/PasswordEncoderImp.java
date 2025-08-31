package co.com.crediya.auth.api.config.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import co.com.crediya.auth.model.helper.PasswordEncoder;
import lombok.Getter;

@Getter
@Component
public class PasswordEncoderImp implements PasswordEncoder {

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public String encode(String value) {
    return encoder.encode(value);
  }
}
