package dev.yubin.imageconverter.api.convert.converter;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToImageFormatConverter implements Converter<String, ImageFormat> {
  @Override
  public ImageFormat convert(String source) {
    return ImageFormat.valueOf(source.toUpperCase());
  }
}
