package dev.yubin.imageconverter.api.messaging.consumer;

import dev.yubin.imageconverter.api.messaging.dto.ImageConvertResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageConvertResultConsumer {

    @RabbitListener(queues = "image.convert.result.queue")
    public void handleConvertResult(ImageConvertResult result) {
        log.info("변환 결과 수신: {}", result);
        // DB 저장 또는 WebSocket 전송 등 처리
    }
}
