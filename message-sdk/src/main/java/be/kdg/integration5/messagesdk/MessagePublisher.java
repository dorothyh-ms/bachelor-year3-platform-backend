package be.kdg.integration5.messagesdk;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String exchange, String routingKey, BaseMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
