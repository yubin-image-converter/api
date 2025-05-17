package dev.yubin.imageconverter.api.auth.controller;

import dev.yubin.imageconverter.api.auth.controller.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.auth.controller.dto.TokenWithUserResponse;
import dev.yubin.imageconverter.api.common.constants.ApiPaths;
import dev.yubin.imageconverter.api.common.dto.ErrorResponse;
import dev.yubin.imageconverter.api.security.jwt.JwtProvider;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/oauth/callback")
    @Operation(summary = "Nest OAuth 콜백 수신", description = "Nest에서 받은 사용자 정보를 받아 JWT를 발급하고 사용자 " +
            "정보를 반환합니다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "로그인 또는 회원가입 성공", content =
    @Content(schema = @Schema(implementation = TokenWithUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 형식 오류 (예: 필수 필드 누락)", content =
            @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content =
            @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류 (예: DB 오류, JWT 발급 실패)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<TokenWithUserResponse> receiveUserInfoFromNest(
            @Valid @RequestBody NestOAuthUserDto userInfo
    ) {
        UserResponseDto user = userService.saveOrLogin(userInfo);

        String jwt = jwtProvider.generateToken(user.getPublicId(),
                                               user.getProvider());

        return ResponseEntity.ok(new TokenWithUserResponse(jwt,
                                                           user));
    }

}
