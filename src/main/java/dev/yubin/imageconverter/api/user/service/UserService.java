package dev.yubin.imageconverter.api.user.service;

import dev.yubin.imageconverter.api.user.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.entity.User;
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
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    public UserResponseDto findMe(String publicId) {
        User user = findByPublicId(publicId);
        return UserResponseDto.from(user);
    }


    /**
     * Nest.js에서 전달된 사용자 정보로 회원가입 또는 로그인 처리
     */
    public UserResponseDto saveOrLogin(NestOAuthUserDto userInfo) {
        log.info("✅ 사용자 정보 수신 from Nest.js: provider={}, email={}",
                 userInfo.getProvider(), userInfo.getEmail());

        // provider + providerId 로 기존 사용자 검색
        User user = userRepository
                .findByProviderAndProviderId(userInfo.getProvider(),
                                             userInfo.getProviderId())
                .orElseGet(() -> {
                    // 없으면 새로 가입
                    log.info("🆕 신규 사용자, 회원가입 진행");
                    return userRepository.save(User
                                                       .builder()
                                                       .email(userInfo.getEmail())
                                                       .name(userInfo.getName())
                                                       .provider(userInfo.getProvider())
                                                       .providerId(userInfo.getProviderId())
                                                       .build());
                });

        return UserResponseDto.from(user);
    }
}
