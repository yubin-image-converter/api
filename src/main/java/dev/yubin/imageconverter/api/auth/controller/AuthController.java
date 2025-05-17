package dev.yubin.imageconverter.api.auth.controller;

import dev.yubin.imageconverter.api.auth.controller.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.auth.controller.dto.TokenWithUserResponse;
import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final JwtProvider jwtProvider;
  private final UserService userService;

  @PostMapping("/oauth/callback")
  public ResponseEntity<TokenWithUserResponse> receiveUserInfoFromNest(
      @RequestBody NestOAuthUserDto userInfo, HttpServletResponse response) {
    UserResponseDto user = userService.saveOrLogin(userInfo);

    String jwt = jwtProvider.generateToken(user.getPublicId(), user.getProvider()); // JWT 발급

    log.info(jwt);
    log.info("accesstoken 발급");
    return ResponseEntity.ok(new TokenWithUserResponse(jwt, user));
  }

  @GetMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    ResponseCookie cookie =
        ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();
    response.addHeader("Set-Cookie", cookie.toString());
    return ResponseEntity.ok().build();
  }
}
