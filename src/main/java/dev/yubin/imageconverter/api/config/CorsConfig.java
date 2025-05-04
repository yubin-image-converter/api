package dev.yubin.imageconverter.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("http://192.168.0.110:5173",
                                                "http://localhost:5173",
                                                "http://localhost:3000",
                                                "https://api.image-converter.yubinshin.com",
                                                "https://auth.image-converter.yubinshin.com",
                                                "https://image-converter.yubinshin.com"));
        // 프론트엔드 주소
        config.setAllowedMethods(List.of("GET",
                                         "POST",
                                         "PUT",
                                         "DELETE",
                                         "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                                         config);

        return source;
    }
}
