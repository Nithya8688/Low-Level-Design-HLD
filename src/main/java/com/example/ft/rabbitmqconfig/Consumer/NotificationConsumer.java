package com.example.ft.rabbitmqconfig.Consumer;
import com.example.ft.caloriestracking.repository.DailyIntakeRepository;
import com.example.ft.caloriestracking.repository.UserTargetRepository;
import com.example.ft.email.service.EmailNotificationService;
import com.example.ft.rabbitmqconfig.RabbitConfig;
import com.example.ft.rabbitmqconfig.dto.DailyIntakeEvent;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;


import java.io.IOException;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer {

    private static final int MAX_RETRIES = 3;

    private final DailyIntakeRepository intakeRepository;
    private final UserTargetRepository targetRepository;
    private final EmailNotificationService emailService;
    private final RabbitTemplate rabbitTemplate;

    public NotificationConsumer(
            DailyIntakeRepository intakeRepository,
            UserTargetRepository targetRepository,
            EmailNotificationService emailService,
            RabbitTemplate rabbitTemplate) {

        this.intakeRepository = intakeRepository;
        this.targetRepository = targetRepository;
        this.emailService = emailService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(
            queues = {
                    RabbitConfig.QUEUE
            },
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleDailyIntake(
            DailyIntakeEvent event,
            Channel channel,
            Message message
    ) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Integer retryCount = (Integer) message
                .getMessageProperties()
                .getHeaders()
                .getOrDefault("x-retry-count", 0);

        try {
            Long userId = event.getUserId();
            LocalDate date = LocalDate.parse(event.getDate());
            if(retryCount==0 || retryCount==1 ){
            throw new RuntimeException("Bad exception for testing");}

            Double totalCalories =
                    intakeRepository.getTotalCalories(userId, date);

            Double totalProtein =
                    intakeRepository.getTotalProtein(userId, date);

            Double totalCarbs =
                    intakeRepository.getTotalCarbs(userId, date);

            Double totalFat =
                    intakeRepository.getTotalFat(userId, date);

            Double targetCalories =
                    targetRepository.findById(userId)
                            .map(t -> t.getDailyCalorieTarget())
                            .orElse(2000.0);

            double percentage = (totalCalories / targetCalories) * 100;

            String subject;
            String body;
            body =
                    "📅 Date: " + date + "\n\n" +
                            "🔥 Calories: " + totalCalories + " kcal\n" +
                            "🥩 Protein: " + totalProtein + " g\n" +
                            "🍚 Carbs: " + totalCarbs + " g\n" +
                            "🧈 Fat: " + totalFat + " g\n\n" +
                            "🎯 Target Calories: " + targetCalories + " kcal";

            if (percentage >= 100) {
                subject = "🚨 Calorie Limit Exceeded";
//                body = "You exceeded your calorie target!\n\n"
//                        + "Consumed: " + totalCalories + " kcal\n"
//                        + "Target: " + targetCalories + " kcal";
            } else if (percentage >= 80) {
                subject = "⚠️ Calorie Warning";
//                body = "You reached 80% of your calorie target.\n\n"
//                        + "Consumed: " + totalCalories + " kcal\n"
//                        + "Target: " + targetCalories + " kcal";
            } else {
                subject = "🚨 Calories Tracker";
//                channel.basicAck(deliveryTag, false);
//                return;
            }

            // 🔔 SEND EMAIL
            emailService.sendEmail(
                    "porikanithyanand@gmail.com",
                    subject,
                    body
            );

            // ✅ SUCCESS → ACK
            channel.basicAck(deliveryTag, false);

            System.out.println("✅ Message ACKED");
        } catch (Exception e) {
//
            Map<String, Object> headers =
                    new HashMap<>(message.getMessageProperties().getHeaders());

            headers.put("x-retry-count", retryCount + 1);

            MessageProperties newProps = new MessageProperties();
            newProps.setHeaders(headers);
            newProps.setContentType(message.getMessageProperties().getContentType());
            newProps.setContentEncoding(message.getMessageProperties().getContentEncoding());
            newProps.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

            Message newMessage = new Message(message.getBody(), newProps);
//
//            if (retryCount < MAX_RETRIES) {
//
//                System.out.println("🔁 Retrying message, attempt=" + (retryCount + 1));
//
//                rabbitTemplate.send(
//                        RabbitConfig.EXCHANGE,
//                        RabbitConfig.RETRY_ROUTING_KEY,
//                        newMessage
//                );
//
//            } else {
//
//                System.out.println("💀 Sending message to DLQ");
//
//                rabbitTemplate.send(
//                        RabbitConfig.EXCHANGE,
//                        RabbitConfig.DLQ_ROUTING_KEY,
//                        newMessage
//                );
//            }
//
//            // ✅ ACK ORIGINAL MESSAGE
//            channel.basicAck(deliveryTag, false);


            String routingKey;

            if (retryCount == 0) {
                routingKey = RabbitConfig.RETRY_KEY_1;
            } else if (retryCount == 1) {
                routingKey = RabbitConfig.RETRY_KEY_2;
            } else if (retryCount == 2) {
                routingKey = RabbitConfig.RETRY_KEY_3;
            } else {
                routingKey = RabbitConfig.DLQ_KEY;
            }
            System.out.println("🔁 Retrying attempt=" + (retryCount + 1));
            headers.put("x-retry-count", retryCount + 1);

            rabbitTemplate.send(
                    RabbitConfig.EXCHANGE,
                    routingKey,
                    newMessage
            );

// ALWAYS ACK original
            channel.basicAck(deliveryTag, false);
        }
    }
}