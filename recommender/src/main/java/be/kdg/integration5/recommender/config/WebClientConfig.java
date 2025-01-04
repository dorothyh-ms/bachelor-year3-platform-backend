package be.kdg.integration5.recommender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${game.recommender.base-url}")
    private String recommenderBaseUrl;
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(recommenderBaseUrl).build();
    }
}