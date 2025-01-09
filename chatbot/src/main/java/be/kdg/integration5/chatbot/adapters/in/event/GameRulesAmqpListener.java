package be.kdg.integration5.chatbot.adapters.in.event;

import be.kdg.integration5.chatbot.ports.in.UploadFileUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Profile("!test")
public class GameRulesAmqpListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRulesAmqpListener.class);
    private static final String TEMP_DIRECTORY = System.getProperty("user.dir") + "/tmp";
    private UploadFileUseCase uploadFileUseCase;

    public GameRulesAmqpListener(UploadFileUseCase uploadFileUseCase) {
        this.uploadFileUseCase = uploadFileUseCase;
    }

    @RabbitListener(queues = "rules.save.queue") // Replace with your actual queue name
    public void receiveRulesName(String rulesName) {
        LOGGER.info("GameRulesAmqpListener received rules name: {}", rulesName);

        // Process the rules name (e.g., load the rules file from a shared location)
        processRulesName(rulesName);
    }

    private void processRulesName(String rulesName) {
        // Example logic to process the rules name
        LOGGER.info("Processing rules with name: {}", rulesName);

        // If the file is stored in a shared location, reconstruct its full path
        String filePath = TEMP_DIRECTORY + rulesName;

        LOGGER.info("File can be accessed at shared location: {}", filePath);

        // Add your custom logic here to handle the file (e.g., read it, validate it, etc.)
        MultipartFile file = null;
        try {
            file = loadFileAsMultipartFile(rulesName);
            uploadFileUseCase.uploadGameRulesFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MultipartFile loadFileAsMultipartFile(String fileName) throws IOException {
        Path filePath = Paths.get(TEMP_DIRECTORY, fileName);
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        // Create a MultipartFile from the file
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return new MockMultipartFile(
                    file.getName(),                 // File name
                    file.getName(),                 // Original file name
                    "application/octet-stream",     // Content type (default to binary)
                    fileInputStream                 // File content
            );
        }
    }
}
