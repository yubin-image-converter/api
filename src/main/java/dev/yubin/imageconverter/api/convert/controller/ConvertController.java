package dev.yubin.imageconverter.api.convert.controller;

import dev.yubin.imageconverter.api.convert.dto.response.ConvertResponse;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.service.ConvertService;
import dev.yubin.imageconverter.api.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/converts")
@RequiredArgsConstructor
@Slf4j
public class ConvertController {
    private final ConvertService convertService;

    @Operation(summary = "이미지 업로드 후 변환 요청")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> convertImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,

            @Parameter(description = "이미지 파일", required = true)
            @RequestPart("file") MultipartFile file,

            @Parameter(description = "변환할 포맷 (예: png, webp, jpeg)", required = true)
            @RequestParam("format") ImageFormat format
    ) {

        String userId = userDetails.getUser().getId();
        log.info("변환요청 도착");
        log.info(userId);



        String requestId = convertService.sendConvertRequest(file, format, userId);
        return ResponseEntity.ok(new ConvertResponse(requestId, userId));    }

}
