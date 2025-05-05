package dev.yubin.imageconverter.api.config;

import dev.yubin.imageconverter.api.convert.converter.StringToImageFormatConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:3000",
                        "http://fe.yubin.localhost:5173",
                        "http://auth.yubin.localhost:3000"
                )
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToImageFormatConverter());
    }

}

