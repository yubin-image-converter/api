package dev.yubin.imageconverter.api.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "에러 응답 포맷")
public class ErrorResponse {

	@Schema(description = "HTTP 상태 코드", example = "500")
	private final int status;

	@Schema(description = "에러 메시지", example = "Internal Server Error")
	private final String message;

	@Schema(description = "에러 발생 시각", example = "2025-05-17T21:50:01")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private final LocalDateTime timestamp;
}
