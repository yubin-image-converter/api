package dev.yubin.imageconverter.api.messaging.publisher;

import dev.yubin.imageconverter.api.messaging.dto.ImageConvertMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageConvertRequestPublisher {

  private final RabbitTemplate rabbitTemplate;

  public void sendImageConvertRequest(ImageConvertMessage message) {
    rabbitTemplate.convertAndSend("image.convert.exchange", "image.convert.routingKey", message);
  }
}
