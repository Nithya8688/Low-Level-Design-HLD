package com.example.ft.rabbitmqconfig.Producer;
import com.example.ft.rabbitmqconfig.RabbitConfig;
import com.example.ft.rabbitmqconfig.dto.DailyIntakeEvent;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DailyIntakeProducer {

    private final RabbitTemplate rabbitTemplate;

    public DailyIntakeProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(DailyIntakeEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
    }
}
