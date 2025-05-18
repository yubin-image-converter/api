package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import dev.yubin.imageconverter.api.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

  @Schema(description = "사용자 공개 ID", example = "usr_01HV8FA31R2FHNKDPP6AFZ1G59")
  private String publicId;

  @Schema(description = "이메일", example = "example@example.com")
  private String email;

  @Schema(description = "닉네임 또는 이름", example = "Yubin")
  private String name;

  @Schema(
      description = "사용자 역할",
      example = "USER",
      allowableValues = {"USER", "ADMIN"})
  private Role role;

  @Schema(description = "OAuth 제공자", example = "google")
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
