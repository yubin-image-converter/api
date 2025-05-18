package dev.yubin.imageconverter.api.user.service;

import dev.yubin.imageconverter.api.auth.controller.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.common.exception.InternalServerException;
import dev.yubin.imageconverter.api.common.exception.NotFoundException;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.Role;
import dev.yubin.imageconverter.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User findByPublicId(String publicId) {
    return userRepository
        .findByPublicId(publicId)
        .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
  }

  public UserResponseDto findMe(String publicId) {
    User user = findByPublicId(publicId);
    return UserResponseDto.from(user);
  }

  /** Nest.js에서 전달된 사용자 정보로 회원가입 또는 로그인 처리 */
  public UserResponseDto saveOrLogin(NestOAuthUserDto userInfo) {
    log.info(
        "사용자 정보 수신 from Nest.js: provider={}, email={}",
        userInfo.getProvider(),
        userInfo.getEmail());

    try {
      User user =
          userRepository
              .findByProviderAndProviderId(userInfo.getProvider(), userInfo.getProviderId())
              .orElseGet(
                  () -> {
                    log.info("신규 사용자, 회원가입 진행");
                    return userRepository.save(
                        User.builder()
                            .email(userInfo.getEmail())
                            .name(userInfo.getName())
                            .provider(userInfo.getProvider())
                            .providerId(userInfo.getProviderId())
                            .role(Role.USER)
                            .build());
                  });

      return UserResponseDto.from(user);

    } catch (Exception e) {
      log.error("사용자 저장 또는 조회 중 예외 발생", e);
      throw new InternalServerException("서버 오류로 인해 로그인에 실패했습니다.");
    }
  }
}
