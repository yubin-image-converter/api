package dev.yubin.imageconverter.api.user.service;

import dev.yubin.imageconverter.api.user.dto.UserRequestDto;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.repository.UserRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto oauthCallback(UserRequestDto dto) {
        Optional<User> userOpt =
                userRepository.findByProviderAndProviderId(dto.getProvider(), dto.getProviderId());
        return userOpt.map(this::signIn).orElseGet(() -> createUser(dto));
    }

    public UserResponseDto signIn(User user) {
        return UserResponseDto.from(user);
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        User user =
                User.builder()
                        .email(dto.getEmail())
                        .provider(dto.getProvider())
                        .providerId(dto.getProviderId())
                        .name(dto.getName())
                        .build();

        userRepository.save(user);

        return UserResponseDto.from(user);

    }

}
