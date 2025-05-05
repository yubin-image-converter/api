package dev.yubin.imageconverter.api.messaging.dto;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageConvertMessage {
    private String requestId;         // ULID 사용
    private String userId;
    private String originalFilename;
    private ImageFormat format;
    private byte[] imageBytes;
}