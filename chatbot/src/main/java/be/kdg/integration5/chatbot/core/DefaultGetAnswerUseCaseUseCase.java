package be.kdg.integration5.chatbot.core;

import be.kdg.integration5.chatbot.ports.in.GetAnswerUseCase;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.springframework.stereotype.Service;

@Service
public class DefaultGetAnswerUseCaseUseCase implements GetAnswerUseCase {

   private final AnswerLoadPort answerLoadPort;

    public DefaultGetAnswerUseCaseUseCase(AnswerLoadPort answerLoadPort) {
        this.answerLoadPort = answerLoadPort;
    }


    @Override
    public String getAnswer(String question) {
        return answerLoadPort.loadAnswer(question);
    }
}
