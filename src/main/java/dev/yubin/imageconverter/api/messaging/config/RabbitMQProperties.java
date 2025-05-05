package dev.yubin.imageconverter.api.messaging.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "rabbitmq.convert")
public class RabbitMQProperties {
    private final String exchange;
    private final String queue;
    private final String routingKey;

    public RabbitMQProperties(String exchange, String queue, String routingKey) {
        this.exchange = exchange;
        this.queue = queue;
        this.routingKey = routingKey;
    }
}