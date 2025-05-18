package dev.yubin.imageconverter.api.auth.controller.dto;

import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "액세스 토큰과 사용자 정보를 포함한 응답")
public class TokenWithUserResponse {
  @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;

  @Schema(description = "로그인한 사용자 정보")
  private UserResponseDto user;
}
