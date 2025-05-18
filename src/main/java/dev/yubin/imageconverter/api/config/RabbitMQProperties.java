package dev.yubin.imageconverter.api.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Slf4j
@ConfigurationProperties(prefix = "rabbitmq.convert")
public class RabbitMQProperties implements InitializingBean {
  private String exchange;
  private String queue;
  private String routingKey;
  private String resultQueue;

  @Override
  public void afterPropertiesSet() {
    log.info("📦 [RabbitMQProperties] exchange = " + exchange);
    log.info("📦 [RabbitMQProperties] queue = " + queue);
    log.info("📦 [RabbitMQProperties] routingKey = " + routingKey);
    log.info("📦 [RabbitMQProperties] resultQueue = " + resultQueue);
  }
}
