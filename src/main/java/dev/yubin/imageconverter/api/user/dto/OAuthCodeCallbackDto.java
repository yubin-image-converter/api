package dev.yubin.imageconverter.api.user.dto;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthCodeCallbackDto {
    private OAuthProvider provider;
    private String code;
    private String state;
}