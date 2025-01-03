package be.kdg.integration5.adapters.out;

import be.kdg.integration5.domain.Game;
import be.kdg.integration5.ports.out.GameRecommendationLoadPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class GameRecommendationAPIAdapter implements GameRecommendationLoadPort {

    private final WebClient webClient;


    public GameRecommendationAPIAdapter(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public List<Game> recommendGamesForUser(UUID userId) {
        Mono<GameRecommendation[]> response = webClient.get()
                .uri(String.format("/player-game-recommendations/%s", userId.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GameRecommendation[].class)
                .log();

        GameRecommendation[] gameRecommendations = response.block();
        if (gameRecommendations != null) {
            return Arrays.stream(gameRecommendations)
                    .map(recommendation -> new Game(
                            recommendation.getId(),
                            recommendation.getName(),
                            recommendation.getDifficulty(),
                            recommendation.getDescription()
                    )).toList();
        }
        return List.of();
    }

    @Override
    public List<Game> recommendSimilarGames(UUID gameId) {
        return List.of();
    }
}
