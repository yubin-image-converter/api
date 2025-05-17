package dev.yubin.imageconverter.api.auth.controller.dto;

import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenWithUserResponse {
  private String accessToken;
  private UserResponseDto user;
}
