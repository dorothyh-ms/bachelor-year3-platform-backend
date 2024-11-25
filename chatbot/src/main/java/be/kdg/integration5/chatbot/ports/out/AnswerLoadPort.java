package be.kdg.integration5.chatbot.ports.out;

import java.util.Optional;

public interface AnswerLoadPort {

    public String loadAnswer(String question);
    Optional <String> loadGameRuleByName(String gameName);
}
