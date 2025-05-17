package dev.yubin.imageconverter.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "rabbitmq.convert")
public class RabbitMQProperties implements InitializingBean {
  private String exchange;
  private String queue;
  private String routingKey;
  private String resultQueue;

  @Override
  public void afterPropertiesSet() {
    System.out.println("📦 [RabbitMQProperties] exchange = " + exchange);
    System.out.println("📦 [RabbitMQProperties] queue = " + queue);
    System.out.println("📦 [RabbitMQProperties] routingKey = " + routingKey);
    System.out.println("📦 [RabbitMQProperties] resultQueue = " + resultQueue);
  }
}
