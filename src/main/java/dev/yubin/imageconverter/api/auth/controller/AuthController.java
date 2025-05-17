package dev.yubin.imageconverter.api.auth.controller;

import dev.yubin.imageconverter.api.auth.controller.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.auth.controller.dto.TokenWithUserResponse;
import dev.yubin.imageconverter.api.common.constants.ApiPaths;
import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class AuthController {

  private final JwtProvider jwtProvider;
  private final UserService userService;

  @PostMapping("/oauth/callback")
  @Operation(
          summary = "Nest OAuth 콜백 수신",
          description = "Nest에서 받은 사용자 정보를 받아 JWT를 발급하고 사용자 정보를 반환합니다."
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "로그인 또는 회원가입 성공"),
          @ApiResponse(responseCode = "400", description = "잘못된 요청 형식 (DTO 파싱 실패 등)"),
          @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  public ResponseEntity<TokenWithUserResponse> receiveUserInfoFromNest(
      @RequestBody NestOAuthUserDto userInfo, HttpServletResponse response) {
    UserResponseDto user = userService.saveOrLogin(userInfo);

    String jwt = jwtProvider.generateToken(user.getPublicId(), user.getProvider()); // JWT 발급

    log.info(jwt);
    log.info("accesstoken 발급");
    return ResponseEntity.ok(new TokenWithUserResponse(jwt, user));
  }

}
