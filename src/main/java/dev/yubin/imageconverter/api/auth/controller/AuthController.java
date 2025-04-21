package dev.yubin.imageconverter.api.auth.controller;

import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.user.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserService userService;


    @PostMapping("/oauth/callback")
    public ResponseEntity<UserResponseDto> receiveUserInfoFromNest(@RequestBody NestOAuthUserDto userInfo, HttpServletResponse response) {
        UserResponseDto user = userService.saveOrLogin(userInfo);

        String jwt = jwtProvider.generateToken(user.getPublicId(), user.getProvider()); // JWT 발급

        // ✅ HttpOnly 쿠키로 전달
        ResponseCookie cookie = ResponseCookie.from("access_token", jwt).httpOnly(true).secure(false) // 프로덕션에선 true + HTTPS 필요!
                .path("/").maxAge(Duration.ofDays(7)).sameSite("Lax") // or Strict
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(user); // 쿠키는 헤더로 보내졌음
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("access_token", "").httpOnly(true).secure(false).path("/").maxAge(0).sameSite("Lax").build();
        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }
}
