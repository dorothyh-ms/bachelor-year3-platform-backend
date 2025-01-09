package be.kdg.integration5.chatbot.ports.in;

import java.util.UUID;

public interface GetAnswerUseCase {
    String getAnswer(String question, UUID userid, String game);
}
