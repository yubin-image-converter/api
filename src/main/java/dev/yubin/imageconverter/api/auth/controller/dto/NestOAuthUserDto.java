package dev.yubin.imageconverter.api.auth.controller.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NestOAuthUserDto {

	@Email
	@NotBlank
	@Schema(description = "사용자 이메일 주소", example = "yubin@example.com")
	private String email;

	@NotBlank
	@Schema(description = "사용자 이름 또는 닉네임", example = "유빈")
	private String name;

	@NotNull
	@Schema(description = "OAuth 제공자", example = "google")
	private OAuthProvider provider;

	@NotBlank
	@Schema(description = "제공자 측 사용자 ID", example = "117283192837192837128")
	private String providerId;
}
