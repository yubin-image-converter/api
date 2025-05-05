package dev.yubin.imageconverter.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue convertQueue() {
        return new Queue("image.convert.queue");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("image.convert.exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(convertQueue())
                .to(exchange())
                .with("image.convert.routingKey");
    }
}
