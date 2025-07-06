package dev.yubin.imageconverter.api.convert.service;

import dev.yubin.imageconverter.api.common.exception.InternalServerException;
import dev.yubin.imageconverter.api.common.exception.NotFoundException;
import dev.yubin.imageconverter.api.common.util.ULIDGenerator;
import dev.yubin.imageconverter.api.config.RabbitMQProperties;
import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import dev.yubin.imageconverter.api.convert.util.NfsUtil;
import dev.yubin.imageconverter.api.messaging.dto.ImageConvertMessage;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {

  private final RabbitTemplate rabbitTemplate;
  private final RabbitMQProperties rabbitMQProperties;
  private final RedisTemplate<String, String> redisTemplate;
  private final NfsUtil nfsUtil;

  public String sendConvertRequest(MultipartFile file, ImageFormat format, String userId) {
    try {
      String requestId = ULIDGenerator.generate();
      String path = nfsUtil.saveFileToNfs(file, userId, requestId);

      log.info("File saved at path: {}", path);

      ImageConvertMessage message =
          new ImageConvertMessage(requestId, userId, path, format.name().toLowerCase());

      rabbitTemplate.convertAndSend(
          rabbitMQProperties.getExchange(), rabbitMQProperties.getRoutingKey(), message);

      return requestId;
    } catch (IOException e) {
      log.error("파일 저장 중 오류", e);
      throw new InternalServerException("NFS 저장 실패" + e);
    }
  }

  public String getAsciiResultUrlOrThrow(String requestId, String hostUrl) {
    String key = "ascii_result:" + requestId;
    String txtPath = redisTemplate.opsForValue().get(key);

    if (txtPath == null) {
      throw new NotFoundException("변환 결과를 찾을 수 없습니다.");
    }

    return hostUrl + txtPath;
  }

  public void saveAsciiResult(String requestId, String txtUrl) {
    String key = "ascii_result:" + requestId;
    redisTemplate.opsForValue().set(key, txtUrl);
  }
}
