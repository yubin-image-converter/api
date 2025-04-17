package dev.yubin.imageconverter.api.user.controller;

import dev.yubin.imageconverter.api.user.dto.UserRequestDto;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/oauth-callback")
  public ResponseEntity<UserResponseDto> oauthCallback(@RequestBody UserRequestDto dto) {
    return ResponseEntity.ok(userService.oauthCallback(dto));
  }
}
