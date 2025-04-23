package dev.yubin.imageconverter.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:5173")
//                .allowedMethods("*").allowCredentials(true);
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://fe.yubin.localhost:5173")
                .allowedOrigins("http://auth.yubin.localhost:3000")
                .allowedMethods("*")
                .allowCredentials(true);
    }

}

