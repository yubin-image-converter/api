package dev.yubin.imageconverter.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Image Converter API", version = "v1"),
    servers = {@Server(url = "/api")})
@Configuration
public class OpenApiConfig {}
