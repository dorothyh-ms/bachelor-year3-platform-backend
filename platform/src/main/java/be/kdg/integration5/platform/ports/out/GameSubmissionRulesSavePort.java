package be.kdg.integration5.platform.ports.out;

import org.springframework.web.multipart.MultipartFile;

public interface GameSubmissionRulesSavePort {

    void saveGameRules(String fileName);
}
