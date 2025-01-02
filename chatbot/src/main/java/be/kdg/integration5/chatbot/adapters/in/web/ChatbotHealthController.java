package be.kdg.integration5.chatbot.adapters.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot-check-health")
public class ChatbotHealthController {
    public ChatbotHealthController() {
    }

    @GetMapping
    public String getHealth() {
        return "all is well!";

    }
}