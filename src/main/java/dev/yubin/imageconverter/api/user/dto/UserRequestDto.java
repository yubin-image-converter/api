package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 요청 DTO (회원가입 또는 인증용)")
public class UserRequestDto {

  @Email(message = "올바른 이메일 형식이어야 합니다.")
  @NotBlank(message = "이메일은 필수입니다.")
  @Schema(description = "사용자 이메일 주소", example = "user@example.com", required = true)
  private String email;

  @NotNull(message = "OAuth 제공자는 필수입니다.")
  @Schema(description = "OAuth 제공자", example = "google", required = true)
  private OAuthProvider provider;

  @NotBlank(message = "OAuth 제공자 ID는 필수입니다.")
  @Schema(
      description = "OAuth 제공자에서 발급한 사용자 고유 ID",
      example = "109283745908127345908",
      required = true)
  private String providerId;

  @NotBlank(message = "사용자 이름은 필수입니다.")
  @Schema(description = "사용자 이름 또는 닉네임", example = "유빈", required = true)
  private String name;
}
