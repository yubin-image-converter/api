package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NestOAuthUserDto {
    private String email;
    private String name;
    private OAuthProvider provider;
    private String providerId;
}
