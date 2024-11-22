package be.kdg.integration5.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("!test")
public class RabbitMQTopology {


    // Queues
    public static final String START_GAME_QUEUE = "start_game_queue";


    // Exchanges
    public static final String START_GAME_COMMANDS = "start_game_commands";


    @Bean
    Queue startGameCommandQueue() {
        return new Queue(START_GAME_QUEUE, false);
    }


    @Bean
    TopicExchange startGameExchange() {
        return new TopicExchange(START_GAME_COMMANDS);
    }


    @Bean
    Binding bindPurchaseOrderFulfilledEventExchangeToPurchaseOrderCommandQueue(
            Queue startGameCommandQueue,
            TopicExchange startGameExchange
    ){
        return BindingBuilder
                .bind(startGameCommandQueue)
                .to(startGameExchange)
                .with("start.command.*");
    }



    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, ObjectMapper mapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(mapper));
        return rabbitTemplate;
    }


    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper mapper) {
        mapper.registerModule(new JavaTimeModule());
        return  new Jackson2JsonMessageConverter(mapper);
    }


}
