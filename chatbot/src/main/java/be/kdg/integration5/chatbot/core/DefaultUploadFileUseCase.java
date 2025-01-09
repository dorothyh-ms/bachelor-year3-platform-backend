package be.kdg.integration5.chatbot.core;

import be.kdg.integration5.chatbot.ports.in.UploadFileUseCase;
import be.kdg.integration5.chatbot.ports.out.RulesSavePort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultUploadFileUseCase implements UploadFileUseCase {
    private RulesSavePort rulesSavePort;

    public DefaultUploadFileUseCase(RulesSavePort rulesSavePort) {
        this.rulesSavePort = rulesSavePort;
    }

    public String uploadGameRulesFile(MultipartFile file) {
        return rulesSavePort.uploadGameRulesFile(file);
    }
}
