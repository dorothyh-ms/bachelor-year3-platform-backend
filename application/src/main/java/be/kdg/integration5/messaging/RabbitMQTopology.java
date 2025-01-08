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

    public static final String MATCH_CREATE_EVENTS_PLATFORM_QUEUE = "match_create_events_platform_queue";
    public static final String MATCH_CREATE_EVENTS_STATISTICS_QUEUE = "match_create_events_statistics_queue";

    public static final String MATCH_END_EVENTS_PLATFORM_QUEUE = "match_end_events_platform_queue";
    public static final String MATCH_END_EVENTS_STATISTICS_QUEUE = "match_end_events_statistics_queue";

    public static final String MATCH_TURN_EVENTS_QUEUE = "turn_events_queue";
    public static final String PLAYER_ACHIEVEMENTS_QUEUE = "player_achievements_queue";

    // Exchanges
    public static final String GAMES_TOPIC_EXCHANGE = "games_topic_exchange";
    public static final String MATCH_CREATED_FANOUT_EXCHANGE = "match_created_fanout_exchange";
    public static final String MATCH_ENDED_FANOUT_EXCHANGE = "match_ended_fanout_exchange";

    @Bean
    public FanoutExchange matchCreatedFanoutExchange() {
        return new FanoutExchange(MATCH_CREATED_FANOUT_EXCHANGE);
    }

    @Bean
    public FanoutExchange matchEndedFanoutExchange() {
        return new FanoutExchange(MATCH_ENDED_FANOUT_EXCHANGE);
    }

    @Bean
    Queue battleshipQueue() {
        return new Queue(BATTLESHIP_START_QUEUE, false);
    }

    @Bean
    Queue turnsQueue() {
        return new Queue(MATCH_TURN_EVENTS_QUEUE, false);
    }

    @Bean
    Queue matchCreatedPlatformQueue() {
        return new Queue(MATCH_CREATE_EVENTS_PLATFORM_QUEUE, false);
    }

    @Bean
    Queue matchCreatedStatisticsQueue() {
        return new Queue(MATCH_CREATE_EVENTS_STATISTICS_QUEUE, false);
    }

    @Bean
    Queue matchEndedPlatformQueue() {
        return new Queue(MATCH_END_EVENTS_PLATFORM_QUEUE, false);
    }

    @Bean
    Queue matchEndedStatisticsQueue() {
        return new Queue(MATCH_END_EVENTS_STATISTICS_QUEUE, false);
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
    Binding bindMatchCreatedFanoutExchangeToPlatformQueue(
            Queue matchCreatedPlatformQueue,
            FanoutExchange matchCreatedFanoutExchange
    ) {
        return BindingBuilder.bind(matchCreatedPlatformQueue).to(matchCreatedFanoutExchange);
    }

    @Bean
    Binding bindMatchCreatedFanoutExchangeToStatisticsQueue(
            Queue matchCreatedStatisticsQueue,
            FanoutExchange matchCreatedFanoutExchange
    ) {
        return BindingBuilder.bind(matchCreatedStatisticsQueue).to(matchCreatedFanoutExchange);
    }

    @Bean
    Binding bindMatchEndedFanoutExchangeToPlatformQueue(
            Queue matchEndedPlatformQueue,
            FanoutExchange matchEndedFanoutExchange
    ) {
        return BindingBuilder.bind(matchEndedPlatformQueue).to(matchEndedFanoutExchange);
    }

    @Bean
    Binding bindMatchEndedFanoutExchangeToStatisticsQueue(
            Queue matchEndedStatisticsQueue,
            FanoutExchange matchEndedFanoutExchange
    ) {
        return BindingBuilder.bind(matchEndedStatisticsQueue).to(matchEndedFanoutExchange);
    }

    @Bean
    Binding bindGameTopicExchangeToBattleshipQueue(
            Queue battleshipQueue,
            TopicExchange gameTopicExchange
    ) {
        return BindingBuilder
                .bind(battleshipQueue)
                .to(gameTopicExchange)
                .with("match.battleship.start");
    }

    @Bean
    Binding bindGameTopicExchangeToTurnEventsQueue(
            Queue turnsQueue,
            TopicExchange gameTopicExchange
    ) {
        return BindingBuilder
                .bind(turnsQueue)
                .to(gameTopicExchange)
                .with("match.turns");
    }

    @Bean
    Binding bindGameTopicExchangeToPlayerAchievementsQueue(
            Queue playerAchievementsQueue,
            TopicExchange gameTopicExchange
    ) {
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
        return new Jackson2JsonMessageConverter(mapper);
    }
}
