package be.kdg.integration5.recommender.adapters.out;

import be.kdg.integration5.recommender.domain.Game;
import be.kdg.integration5.recommender.ports.out.GameRecommendationLoadPort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class GameRecommendationAPIAdapter implements GameRecommendationLoadPort {

    private final WebClient webClient;


    public GameRecommendationAPIAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Game> recommendGamesForUser(UUID userId) {
        return webClient.get()
                .uri(String.format("/player-game-recommendations/%s", userId.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        clientResponse -> {
                            return Mono.empty(); // Will result in the Mono being completed without data
                        }
                )
                .bodyToMono(GameRecommendation[].class)
                .onErrorResume(e -> {
                    // Log any unexpected error
                    System.err.println("Unexpected error while fetching recommendations: " + e.getMessage());
                    return Mono.just(new GameRecommendation[0]); // Fallback to empty array
                })
                .blockOptional()
                .map(Arrays::asList)
                .orElse(List.of())
                .stream()
                .map(recommendation -> new Game(
                        recommendation.getId(),
                        recommendation.getName()
                ))
                .toList();
    };

    @Override
    public List<Game> recommendSimilarGames(UUID gameId) {
        return List.of();
    }
}
