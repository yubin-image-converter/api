package dev.yubin.imageconverter.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    public RabbitMQConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Queue convertQueue() {
        System.out.println("🐛 DEBUG queue name = " + properties.getQueue()); // 여기 null이면 → 주입 실패
        return new Queue(properties.getQueue());
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(properties.getResultQueue(), true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(convertQueue())
                .to(exchange())
                .with(properties.getRoutingKey());
    }
}
