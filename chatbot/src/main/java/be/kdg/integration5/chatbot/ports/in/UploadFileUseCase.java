package be.kdg.integration5.chatbot.ports.in;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUseCase {
    String uploadGameRulesFile(MultipartFile file);

}
