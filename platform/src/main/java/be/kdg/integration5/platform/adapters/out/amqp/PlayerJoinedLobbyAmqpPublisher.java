package be.kdg.integration5.platform.adapters.out.amqp;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.StartGameCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
@Profile("!test")
public class PlayerJoinedLobbyAmqpPublisher implements LobbyJoinedPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerJoinedLobbyAmqpPublisher.class);

    private static final String EXCHANGE_NAME = "games_topic_exchange";
    private final RabbitTemplate rabbitTemplate;

    public PlayerJoinedLobbyAmqpPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void lobbyJoined(Lobby lobby) {
        LOGGER.info("PlayerJoinedLobbyAmqpPublisher is running lobbyJoined with lobby {}", lobby);
        String routingKey = String.format("game.%s.start", lobby.getGame().getName().toLowerCase());

        StartGameCommand command = new StartGameCommand(
                lobby.getInitiatingPlayer().getPlayerId(),
                lobby.getJoinedPlayer().getPlayerId(),
                lobby.getMatchId()
        );
        LOGGER.info("PlayerJoinedLobbyAmqpPublisher is sending command {} with routing key {}", command, routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, command);
    }
}
