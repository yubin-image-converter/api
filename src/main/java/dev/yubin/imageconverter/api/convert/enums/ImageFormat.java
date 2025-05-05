package dev.yubin.imageconverter.api.convert.enums;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미지 변환 포맷")
public enum ImageFormat {
    @Schema(description = "JPG 형식")
    JPG,

    @Schema(description = "PNG 형식")
    PNG,

    @Schema(description = "WEBP 형식")
    WEBP
}