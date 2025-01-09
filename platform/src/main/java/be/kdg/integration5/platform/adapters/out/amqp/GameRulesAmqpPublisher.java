package be.kdg.integration5.platform.adapters.out.amqp;


import be.kdg.integration5.platform.ports.out.GameSubmissionRulesSavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("!test")
public class GameRulesAmqpPublisher implements GameSubmissionRulesSavePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRulesAmqpPublisher.class);

    private static final String EXCHANGE_NAME = "rules_topic_exchange";
    private final RabbitTemplate rabbitTemplate;

    public GameRulesAmqpPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void saveGameRules(String rules) {

        // Construct the routing key (example: "rules.save")
        String routingKey = "rules.save";

        // Publish the rules name as the message payload
        LOGGER.info("GameRulesAmqpPublisher is sending rules name '{}' with routing key {}", rules, routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, rules);
    }



}
