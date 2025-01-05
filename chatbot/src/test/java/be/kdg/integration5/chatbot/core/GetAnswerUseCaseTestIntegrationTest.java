package be.kdg.integration5.chatbot.core;

import be.kdg.integration5.chatbot.ports.in.GetAnswerUseCase;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetAnswerUseCaseTestIntegrationTest extends AbstractChatbotTest{

    @Autowired
    private GetAnswerUseCase getAnswerUseCase;


    @Test
    void shouldReturnResponseFromChatbotService() {
        //Arrange
        String question = "What are the rules of the game?";
        UUID playerId = UUID.randomUUID();
        String game = "Battleship";
        String answer = getAnswerUseCase.getAnswer(question, playerId, game);
        //Assert
        assertNotEquals(answer, "Sorry, I couldn't process your question.");


    }
}