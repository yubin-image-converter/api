package dev.yubin.imageconverter.api.convert.service;

import dev.yubin.imageconverter.api.common.util.ULIDGenerator;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.messaging.dto.ImageConvertMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.convert}")
    private String convertExchange;

    @Value("${rabbitmq.routing-key.convert}")
    private String convertRoutingKey;
    

    public void sendConvertRequest(MultipartFile file, ImageFormat format, String userId) {
        try {
            String requestId = ULIDGenerator.generate();

            ImageConvertMessage message = new ImageConvertMessage(
                    requestId,
                    userId,
                    file.getOriginalFilename(),
                    format,
                    file.getBytes() // 여기서 IOException 발생 가능
            );

            rabbitTemplate.convertAndSend(convertExchange, convertRoutingKey, message);

        } catch (IOException e) {
            // 로깅하거나 예외 처리
            log.error("파일을 바이트 배열로 변환하는 중 오류 발생", e);
            throw new RuntimeException("파일 처리 실패", e);
        }
    }
}
