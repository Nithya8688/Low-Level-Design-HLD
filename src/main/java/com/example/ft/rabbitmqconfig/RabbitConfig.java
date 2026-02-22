package com.example.ft.rabbitmqconfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "calorie.exchange";

    // Main
    public static final String QUEUE = "daily.intake.queue";
    public static final String ROUTING_KEY = "daily.intake.logged";

    // Retry routing keys
    public static final String RETRY_KEY_1 = "daily.intake.retry.1";
    public static final String RETRY_KEY_2 = "daily.intake.retry.2";
    public static final String RETRY_KEY_3 = "daily.intake.retry.3";

    // Retry queues
    public static final String RETRY_QUEUE_1 = "daily.intake.retry.queue.1";
    public static final String RETRY_QUEUE_2 = "daily.intake.retry.queue.2";
    public static final String RETRY_QUEUE_3 = "daily.intake.retry.queue.3";

    // DLQ
    public static final String DLQ_QUEUE = "daily.intake.dlq";
    public static final String DLQ_KEY = "daily.intake.dlq";


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Queue dlqQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue retryQueue1() {
        return QueueBuilder.durable(RETRY_QUEUE_1)
                .withArgument("x-message-ttl", 2000)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue retryQueue2() {
        return QueueBuilder.durable(RETRY_QUEUE_2)
                .withArgument("x-message-ttl", 5000)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue retryQueue3() {
        return QueueBuilder.durable(RETRY_QUEUE_3)
                .withArgument("x-message-ttl", 10000)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding dlqBinding(Queue dlqQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(dlqQueue)
                .to(exchange)
                .with(DLQ_KEY);
    }

    @Bean
    public Binding retry1Binding(TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue1())
                .to(exchange)
                .with(RETRY_KEY_1);
    }

    @Bean
    public Binding retry2Binding(TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue2())
                .to(exchange)
                .with(RETRY_KEY_2);
    }

    @Bean
    public Binding retry3Binding(TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue3())
                .to(exchange)
                .with(RETRY_KEY_3);
    }

    // ✅ CORRECT JSON CONVERTER (Spring AMQP 4.x)
    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, JacksonJsonMessageConverter converter
    ) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
}
