package dev.yubin.imageconverter.api.convert.controller;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.service.ConvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/converts")
@RequiredArgsConstructor
public class ConvertController {
    private final ConvertService convertService;

    @Operation(summary = "이미지 업로드 후 변환 요청")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> convertImage(
            @Parameter(description = "이미지 파일", required = true)
            @RequestPart("file") MultipartFile file,

            @Parameter(description = "변환할 포맷 (예: png, webp, jpeg)", required = true)
            @RequestPart("format") ImageFormat format
    ) {
        convertService.convert(file, format);
        return ResponseEntity.ok("변환 요청 전송 완료!");
    }

}
