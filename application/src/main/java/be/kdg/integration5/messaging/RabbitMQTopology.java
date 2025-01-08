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

    public static final String MATCH_CREATE_EVENTS_QUEUE = "match_create_events_queue";

    public static final String MATCH_END_EVENTS_QUEUE = "match_end_events_queue";

    public static final String MATCH_TURN_EVENTS_QUEUE = "turn_events_queue";

    public static final String PLAYER_ACHIEVEMENTS_QUEUE = "player_achievements_queue";



    // Exchanges
    public static final String GAMES_TOPIC_EXCHANGE = "games_topic_exchange";




    @Bean
    Queue battleshipQueue() {
        return new Queue(BATTLESHIP_START_QUEUE, false);
    }

    @Bean
    Queue turnsQueue() {
        return new Queue(MATCH_TURN_EVENTS_QUEUE, false);
    }

    @Bean
    Queue matchCreatedQueue() {
        return new Queue(MATCH_CREATE_EVENTS_QUEUE, false);
    }

    @Bean
    Queue matchEndedQueue() {
        return new Queue(MATCH_END_EVENTS_QUEUE, false);
    }

    @Bean
    Queue playerAchievementsQueue() {
        return new Queue(PLAYER_ACHIEVEMENTS_QUEUE, false);
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
                .with("match.battleship.start");
    }

    @Bean
    Binding bindGameTopicExchangeToTurnEventsQueue(
            Queue turnsQueue,
            TopicExchange gameTopicExchange
    ){
        return BindingBuilder
                .bind(turnsQueue)
                .to(gameTopicExchange)
                .with("match.turns");
    }

    @Bean
    Binding bindGameTopicExchangeToMatchCreateEventsQueue(
            Queue matchCreatedQueue,
            TopicExchange gameTopicExchange
    ){
        return BindingBuilder
                .bind(matchCreatedQueue)
                .to(gameTopicExchange)
                .with("match.created");
    }

    @Bean
    Binding bindGameTopicExchangeToMatchEndedEventsQueue(
            Queue matchEndedQueue,
            TopicExchange gameTopicExchange
    ){
        return BindingBuilder
                .bind(matchEndedQueue)
                .to(gameTopicExchange)
                .with("match.ended");
    }

    @Bean
    Binding bindGameTopicExchangeToPlayerAchievementsQueue(
            Queue playerAchievementsQueue,
            TopicExchange gameTopicExchange
    ){
        return BindingBuilder
                .bind(playerAchievementsQueue)
                .to(gameTopicExchange)
                .with("player.achievements");
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
