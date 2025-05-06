package dev.yubin.imageconverter.api.messaging.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageConvertMessage implements Serializable {
    private String requestId;
    private String userId;
    private String path;           // 파일 경로만 전달
    private String targetFormat;   // 예: png, jpeg, webp
}