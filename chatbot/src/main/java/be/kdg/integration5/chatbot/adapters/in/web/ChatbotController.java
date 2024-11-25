package be.kdg.integration5.chatbot.adapters.in.web;

import be.kdg.integration5.chatbot.ports.in.GetAnswerUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController{

    private final GetAnswerUseCase getAnswerUseCase;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotController.class);

    public ChatbotController(GetAnswerUseCase getAnswerUseCase) {
        this.getAnswerUseCase = getAnswerUseCase;
    }
}
