package dev.yubin.imageconverter.api.convert.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;

@Component
public class StringToImageFormatConverter implements Converter<String, ImageFormat> {
	@Override
	public ImageFormat convert(String source) {
		return ImageFormat.valueOf(source.toUpperCase());
	}
}
