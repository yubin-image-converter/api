package dev.yubin.imageconverter.api.messaging.dto;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageConvertResult {
    private String requestId;       // 동일 ULID로 추적
    private boolean success;
    private String downloadUrl;
    private String errorMessage;
    private ImageFormat format;       // 최종 포맷
//    private String requestId;          // 요청 ID (매핑용)
//    private boolean success;          // 성공 여부
//    private String message;           // 오류 메시지 또는 성공 설명
//    private String convertedFilePath; // 변환된 이미지 경로

}