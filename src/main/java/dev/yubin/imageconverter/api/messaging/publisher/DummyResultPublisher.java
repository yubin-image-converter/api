package dev.yubin.imageconverter.api.messaging.publisher;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.messaging.dto.ImageConvertResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyResultPublisher {

    private final RabbitTemplate rabbitTemplate;

    public DummyResultPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDummyResult(String requestId, ImageFormat format, String userId) {
        log.info("🐛 더미 결과 발행 중: {}", requestId);

        ImageConvertResult fakeResult = new ImageConvertResult(
                requestId,
                true,
                "https://dummyimage.com/600x400/000/fff." + requestId + "." + format.name().toLowerCase(),
                null,
                format
        );


        rabbitTemplate.convertAndSend(
                "image.convert.result.queue", // Consumer에서 듣는 큐 이름
                fakeResult
        );
    }
}