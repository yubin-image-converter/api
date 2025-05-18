package dev.yubin.imageconverter.api.user.controller;

import dev.yubin.imageconverter.api.common.constants.ApiPaths;
import dev.yubin.imageconverter.api.common.dto.ErrorResponse;
import dev.yubin.imageconverter.api.common.exception.UnauthorizedException;
import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.security.userdetails.CustomUserDetails;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPaths.USERS)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtProvider jwtProvider;

  @Operation(summary = "내 정보 조회", description = "현재 인증된 사용자의 정보를 반환합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공적으로 사용자 정보를 반환함"),
    @ApiResponse(
        responseCode = "401",
        description = "인증되지 않은 사용자",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/me")
  public UserResponseDto findMe() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof CustomUserDetails userDetails) {
      return UserResponseDto.from(userDetails.getUser());
    } else {
      throw new UnauthorizedException("인증되지 않은 사용자입니다.");
    }
  }
}
