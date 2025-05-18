package dev.yubin.imageconverter.api.convert.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "이미지 변환 요청 응답 DTO")
public class ConvertResponse {

  @Schema(description = "요청 식별자 (ULID)", example = "01HVYZ5YZXYZTR4EGGM6KJ0E9P")
  private String requestId;

  @Schema(description = "요청한 사용자 ID", example = "usr_01HVXXY9JPWT7Q6NR6Z8FJZTHQ")
  private String userId;
}
