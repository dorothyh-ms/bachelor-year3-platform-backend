package be.kdg.integration5.chatbot.ports.out;
import java.util.Optional;
import java.util.UUID;

public interface AnswerLoadPort {

    public String loadAnswer(String question, UUID userId, String game);
    Optional <String> loadGameRuleByName(String gameName);
}
