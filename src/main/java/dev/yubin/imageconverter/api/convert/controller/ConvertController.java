package dev.yubin.imageconverter.api.convert.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.yubin.imageconverter.api.common.constants.ApiPaths;
import dev.yubin.imageconverter.api.common.dto.ErrorResponse;
import dev.yubin.imageconverter.api.convert.dto.request.AsciiCompleteRequest;
import dev.yubin.imageconverter.api.convert.dto.response.ConvertResponse;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.service.ConvertService;
import dev.yubin.imageconverter.api.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiPaths.CONVERTS)
@RequiredArgsConstructor
@Slf4j
public class ConvertController {

	private final ConvertService convertService;

	@Value("${app.host-url:http://localhost:8080}")
	private String hostUrl;

	@Operation(summary = "이미지 업로드 후 변환 요청")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "요청 성공", content = @Content),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> convertImage(@AuthenticationPrincipal CustomUserDetails userDetails,
		@Parameter(description = "이미지 파일", required = true) @RequestPart("file") MultipartFile file,
		@Parameter(description = "변환할 포맷 (예: png, webp, jpeg)", required = true) @RequestParam("format") ImageFormat format) {

		String userId = userDetails.getUser().getPublicId();
		String requestId = convertService.sendConvertRequest(file, format, userId);
		return ResponseEntity.ok(new ConvertResponse(requestId, userId));
	}

	@Operation(summary = "변환된 ASCII 결과 URL 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "결과 반환 성공", content = @Content(
			mediaType = "application/json",
			schema = @Schema(example = "{\"txtUrl\": \"https://api.image-converter.yubinshin.com/uploads/example.txt\"}")
		)),
		@ApiResponse(responseCode = "404", description = "결과를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/result")
	public ResponseEntity<?> getConvertResult(@RequestParam("requestId") String requestId) {
		String txtUrl = convertService.getAsciiResultUrlOrThrow(requestId, hostUrl);
		return ResponseEntity.ok(Map.of("txtUrl", txtUrl));
	}


	@Operation(summary = "ASCII 변환 완료 결과 수신 (워커 → 서버)")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "결과 저장 성공", content = @Content(
			mediaType = "application/json",
			schema = @Schema(example = "{\"message\": \"변환 결과 저장 완료\"}")
		)),
		@ApiResponse(responseCode = "400", description = "요청 형식 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/complete")
	public ResponseEntity<?> completeAscii(@Valid @RequestBody AsciiCompleteRequest request) {
		convertService.saveAsciiResult(request.getRequestId(), request.getTxtUrl());
		return ResponseEntity.ok(Map.of("message", "변환 결과 저장 완료"));
	}

}
