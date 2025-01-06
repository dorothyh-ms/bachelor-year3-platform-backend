package be.kdg.integration5.chatbot.adapters.out;

import be.kdg.integration5.chatbot.adapters.in.web.ChatbotController;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatbotApiAdapter implements AnswerLoadPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotApiAdapter.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl;

    public ChatbotApiAdapter(@Value("${chatbot.api.url}") String pythonApiUrl) {
        LOGGER.info("Creating ChatbotApiAdapter with API URL {}", pythonApiUrl);
        this.pythonApiUrl = pythonApiUrl;
    }

    @Override
    public String loadAnswer(String question, UUID userId, String game) {
        // Create the payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId.toString());
        requestBody.put("question", question);
        requestBody.put("game", game);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with headers and payload
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        LOGGER.info("ChatbotApiAdapter is sending request {} to {}", requestBody, pythonApiUrl);

        return restTemplate.postForObject(pythonApiUrl, requestEntity, String.class);
    }

    @Override
    public Optional<String> loadGameRuleByName(String gameName) {
        return Optional.empty();
    }
}
