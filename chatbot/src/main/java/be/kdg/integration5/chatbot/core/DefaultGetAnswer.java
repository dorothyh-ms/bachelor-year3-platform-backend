package be.kdg.integration5.chatbot.core;

import be.kdg.integration5.chatbot.ports.in.GetAnswer;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.springframework.stereotype.Service;

//@Service
public class DefaultGetAnswer implements GetAnswer {

   private final AnswerLoadPort answerLoadPort;

    public DefaultGetAnswer(AnswerLoadPort answerLoadPort) {
        this.answerLoadPort = answerLoadPort;
    }


    @Override
    public String getAnswer(String question) {
        return answerLoadPort.loadAnswer(question);
    }
}
