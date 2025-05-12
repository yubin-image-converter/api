package dev.yubin.imageconverter.api.convert.controller;

import dev.yubin.imageconverter.api.convert.dto.request.AsciiCompleteRequest;
import dev.yubin.imageconverter.api.convert.dto.response.ConvertResponse;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.service.ConvertService;
import dev.yubin.imageconverter.api.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/converts")
@RequiredArgsConstructor
@Slf4j
public class ConvertController {
    private final ConvertService convertService;

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${app.host-url:http://localhost:8080}")
    private String hostUrl;


    @Operation(summary = "이미지 업로드 후 변환 요청")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> convertImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,

            @Parameter(description = "이미지 파일", required = true)
            @RequestPart("file") MultipartFile file,

            @Parameter(description = "변환할 포맷 (예: png, webp, jpeg)", required = true)
            @RequestParam("format") ImageFormat format
    ) {

        String userId = userDetails.getUser().getPublicId();
        log.info("변환요청 도착");
        log.info(userId);



        String requestId = convertService.sendConvertRequest(file, format, userId);
        return ResponseEntity.ok(new ConvertResponse(requestId, userId));
    }



    @GetMapping("/result")
    @Operation(summary = "변환된 ASCII 결과 URL 조회")
    public ResponseEntity<?> getConvertResult(
            @RequestParam("requestId") String requestId
    ) {
        String key = "ascii_result:" + requestId;
        String txtPath = redisTemplate.opsForValue().get(key);
        log.info(requestId);
        if (txtPath == null) {
            return ResponseEntity.status(404).body(Map.of("message", "결과를 찾을 수 없습니다"));
        }

        String fullUrl = hostUrl + "/api" + txtPath; // ✅ 절대경로로 변환
        log.info(fullUrl);
        return ResponseEntity.ok(Map.of("txtUrl", fullUrl));
    }


    @PostMapping("/complete")
    @Operation(summary = "ASCII 변환 완료 결과 수신 (워커 → 서버)")
    public ResponseEntity<?> completeAscii(
            @Valid @RequestBody AsciiCompleteRequest request
    ) {
        log.info("✅ ASCII 변환 완료 수신: requestId={}, userId={}", request.getRequestId(), request.getUserId());

        String key = "ascii_result:" + request.getRequestId();
        redisTemplate.opsForValue().set(key, request.getTxtUrl());

        return ResponseEntity.ok(Map.of("message", "변환 결과 저장 완료"));
    }
}
