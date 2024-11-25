package be.kdg.integration5.chatbot.adapters.out;

import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class PythonFastApiAdapter implements AnswerLoadPort {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl = "http://localhost:8000/api/chatbot";
    @Override
    public String loadAnswer(String question) {
        // Call the Python FastAPI endpoint
        String response = restTemplate.postForObject(pythonApiUrl, question, String.class);
        return response != null ? response : "Sorry, I couldn't process your question.";
    }

    @Override
    public Optional<String> loadGameRuleByName(String gameName) {
        return Optional.empty();
    }
}
