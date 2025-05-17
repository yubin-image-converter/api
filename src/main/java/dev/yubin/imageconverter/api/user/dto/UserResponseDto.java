package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import dev.yubin.imageconverter.api.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

  private String publicId;
  private String email;
  private String name;
  private Role role;
  private OAuthProvider provider;

  public static UserResponseDto from(User user) {
    return UserResponseDto.builder()
        .publicId(user.getPublicId())
        .email(user.getEmail())
        .name(user.getName())
        .provider(user.getProvider())
        .role(user.getRole())
        .build();
  }
}
