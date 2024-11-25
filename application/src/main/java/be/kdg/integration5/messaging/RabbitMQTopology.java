package be.kdg.integration5.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQTopology {


    // Queues
    public static final String BATTLESHIP_START_QUEUE = "battleship_queue";


    // Exchanges
    public static final String GAMES_TOPIC_EXCHANGE = "games_topic_exchange";


    @Bean
    Queue battleshipQueue() {
        return new Queue(BATTLESHIP_START_QUEUE, false);
    }


    @Bean
    TopicExchange gameTopicExchange() {
        return new TopicExchange(GAMES_TOPIC_EXCHANGE);
    }


    @Bean
    Binding bindGameTopicExchangeToBattleshipQueue(
            Queue battleshipQueue,
            TopicExchange gameTopicExchange
    ){
        return BindingBuilder
                .bind(battleshipQueue)
                .to(gameTopicExchange)
                .with("game.battleship.start");
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
