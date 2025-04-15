package dev.yubin.imageconverter.api.user.service;

import dev.yubin.imageconverter.api.user.dto.UserRequestDto;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = User.builder()
                .email(dto.email())
                .name(dto.name())
                .password(dto.password())
                .build();

        User saved = userRepository.save(user);
        return new UserResponseDto(saved.getId(), saved.getEmail(), saved.getName());
    }
}