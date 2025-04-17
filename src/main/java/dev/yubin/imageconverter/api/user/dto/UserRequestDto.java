package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import lombok.Getter;

@Getter
public class UserRequestDto {
  private String email;
  private OAuthProvider provider;
  private String providerId;
  private String name;
}
