package dev.yubin.imageconverter.api.convert.service;

import dev.yubin.imageconverter.api.common.util.ULIDGenerator;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.messaging.dto.ImageConvertMessage;
import dev.yubin.imageconverter.api.config.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public void sendConvertRequest(MultipartFile file, ImageFormat format, String userId) {
        try {
            String requestId = ULIDGenerator.generate();

            ImageConvertMessage message = new ImageConvertMessage(
                    requestId,
                    userId,
                    file.getOriginalFilename(),
                    format,
                    file.getBytes()
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchange(),
                    rabbitMQProperties.getRoutingKey(),
                    message
            );

        } catch (IOException e) {
            log.error("파일을 바이트 배열로 변환하는 중 오류 발생", e);
            throw new RuntimeException("파일 처리 실패", e);
        }
    }
}
