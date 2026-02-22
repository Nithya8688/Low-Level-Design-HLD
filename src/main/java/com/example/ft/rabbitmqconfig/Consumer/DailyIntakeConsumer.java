package com.example.ft.rabbitmqconfig.Consumer;

import com.example.ft.rabbitmqconfig.RabbitConfig;
import com.example.ft.rabbitmqconfig.dto.DailyIntakeEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DailyIntakeConsumer {

//    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(DailyIntakeEvent event) {
        System.out.println(
                "🐰 Received event → userId=" +
                        event.getUserId() +
                        ", date=" +
                        event.getDate()
        );
    }
}

