package dev.yubin.imageconverter.api.config;

import dev.yubin.imageconverter.api.convert.converter.StringToImageFormatConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${nfs.root}")
  private String nfsRootPath;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(
            "http://localhost:5173",
            "http://localhost:3000",
            "https://image-converter.yubinshin.com",
            "https://api.image-converter.yubinshin.com",
            "https://auth.image-converter.yubinshin.com",
            "https://api.image-converter.yubinshin.com/swagger-ui/index.html")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new StringToImageFormatConverter());
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + nfsRootPath + "/");
  }
}
