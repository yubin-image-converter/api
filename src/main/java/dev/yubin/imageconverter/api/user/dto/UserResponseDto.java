package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
  private String id;
  private String email;
  private String name;
  private OAuthProvider provider;

  public static UserResponseDto from(User user) {
    return UserResponseDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .provider(user.getProvider())
        .build();
  }
}
