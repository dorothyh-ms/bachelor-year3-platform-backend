package be.kdg.integration5.chatbot.adapters.out;

import be.kdg.integration5.chatbot.adapters.in.web.ChatbotController;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import be.kdg.integration5.chatbot.ports.out.RulesSavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatbotApiAdapter implements AnswerLoadPort, RulesSavePort {

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
        String pythonQuestionApiUrl = pythonApiUrl + "/question/";
        // Make the POST request
        LOGGER.info("ChatbotApiAdapter is sending request {} to {}", requestBody, pythonQuestionApiUrl);

        return restTemplate.postForObject(pythonQuestionApiUrl, requestEntity, String.class);
    }

    @Override
    public Optional<String> loadGameRuleByName(String gameName) {
        return Optional.empty();
    }

    public String uploadGameRulesFile(MultipartFile file)  {
        // Ensure the file is a JSON file before uploading
        if (!file.getOriginalFilename().endsWith(".json")) {
            throw new IllegalArgumentException("Only .json files are allowed.");
        }

        // Prepare the multipart request for uploading the file
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Prepare the file part in the multipart form
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        // Create the HttpEntity with body and headers
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // Make the API call to upload the file
        String uploadUrl = pythonApiUrl + "/add-rules/";
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        // Return the API response message
        return response.getBody();
    }
}
