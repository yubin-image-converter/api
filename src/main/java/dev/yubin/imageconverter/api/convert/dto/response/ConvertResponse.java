package dev.yubin.imageconverter.api.convert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConvertResponse {
  private String requestId;
  private String userId;
}
