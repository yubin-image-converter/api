package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "OAuth 콜백 요청 DTO")
public class OAuthCodeCallbackDto {
  @Schema(description = "OAuth 제공자", example = "google")
  private OAuthProvider provider;

  @Schema(description = "OAuth 서버로부터 전달받은 인가 코드", example = "4/0AX4XfWjZxyz...")
  private String code;

  @Schema(description = "CSRF 방지를 위한 state 파라미터", example = "xyz123abc")
  private String state;
}
