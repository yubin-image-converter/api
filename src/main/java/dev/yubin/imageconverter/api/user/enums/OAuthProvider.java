package dev.yubin.imageconverter.api.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OAuthProvider {
  GOOGLE,
  NAVER,
  KAKAO;

  @JsonCreator
  public static OAuthProvider from(String value) {
    return OAuthProvider.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toValue() {
    return this.name().toLowerCase();
  }
}
