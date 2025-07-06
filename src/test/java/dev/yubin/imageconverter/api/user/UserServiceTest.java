package dev.yubin.imageconverter.api.user;

import dev.yubin.imageconverter.api.auth.controller.dto.NestOAuthUserDto;
import dev.yubin.imageconverter.api.common.exception.NotFoundException;
import dev.yubin.imageconverter.api.user.dto.UserResponseDto;
import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import dev.yubin.imageconverter.api.user.enums.Role;
import dev.yubin.imageconverter.api.user.repository.UserRepository;
import dev.yubin.imageconverter.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void findByPublicId_shouldReturnUser_whenUserExists() {
        String publicId = "test-123";
        User mockUser = User.builder().publicId(publicId).build();

        when(userRepository.findByPublicId(publicId)).thenReturn(Optional.of(mockUser));

        User result = userService.findByPublicId(publicId);

        assertEquals(publicId, result.getPublicId());
    }

    @Test
    void findByPublicId_shouldThrowException_whenUserNotFound() {
        String publicId = "not-exist";

        when(userRepository.findByPublicId(publicId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.findByPublicId(publicId);
        });
    }

    @Test
    void saveOrLogin_shouldReturnExistingUser_whenUserExists() {
        NestOAuthUserDto dto = new NestOAuthUserDto(
                "test@example.com",       // email
                "홍길동",                  // name
                OAuthProvider.GOOGLE,     // provider
                "12345"                   // providerId
        );

        User existingUser = User.builder()
                .publicId("test-123")
                .email("test@example.com")
                .name("홍길동")
                .provider(OAuthProvider.GOOGLE)
                .providerId("12345")
                .role(Role.USER)
                .build();

        when(userRepository.findByProviderAndProviderId(OAuthProvider.GOOGLE, "12345"))
                .thenReturn(Optional.of(existingUser));

        UserResponseDto result = userService.saveOrLogin(dto);

        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void saveOrLogin_shouldCreateUser_whenUserDoesNotExist() {
        NestOAuthUserDto dto = new NestOAuthUserDto(
                "new@example.com",       // email
                "새유저",                  // name
                OAuthProvider.GOOGLE,     // provider
                "67890"                   // providerId
        );

        when(userRepository.findByProviderAndProviderId(OAuthProvider.GOOGLE, "67890"))
                .thenReturn(Optional.empty());

        User newUser = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .provider(OAuthProvider.GOOGLE)
                .providerId(dto.getProviderId())
                .role(Role.USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        UserResponseDto result = userService.saveOrLogin(dto);

        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
