package be.kdg.integration5.chatbot.ports.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RulesSavePort {
    String uploadGameRulesFile(MultipartFile file);
}
