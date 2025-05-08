package dev.yubin.imageconverter.api.convert.service;

import dev.yubin.imageconverter.api.common.util.ULIDGenerator;
import dev.yubin.imageconverter.api.config.RabbitMQProperties;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.util.NfsUtil;
import dev.yubin.imageconverter.api.messaging.dto.ImageConvertMessage;
import dev.yubin.imageconverter.api.messaging.publisher.DummyResultPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQProperties rabbitMQProperties;

    private final DummyResultPublisher dummyResultPublisher;
    @Value("${spring.profiles.active:}") // 선택적으로 적용
    private String activeProfile;

    @Autowired
    private NfsUtil nfsUtil;

    public String sendConvertRequest(MultipartFile file, ImageFormat format, String userId) {
        try {
            String requestId = ULIDGenerator.generate();

            // NFS에 저장
            String path = nfsUtil.saveFileToNfs(file, userId, requestId);

            // 메시지 구성
            ImageConvertMessage message = new ImageConvertMessage(
                    requestId,
                    userId,
                    path,
                    format.name().toLowerCase() // "png", "jpeg" 등
            );

            // 전송
            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchange(),
                    rabbitMQProperties.getRoutingKey(),
                    message
            );

            if ("local".equals(activeProfile)) {
                dummyResultPublisher.sendDummyResult(requestId, format, userId);
            }
            return requestId;

        } catch (IOException e) {
            log.error("파일 저장 중 오류", e);
            throw new RuntimeException("NFS 저장 실패", e);
        }
    }

}
