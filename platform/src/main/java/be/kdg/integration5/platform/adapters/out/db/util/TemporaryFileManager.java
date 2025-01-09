package be.kdg.integration5.platform.adapters.out.db.util;
import com.sun.tools.javac.Main;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemporaryFileManager {

    // Fixed temporary directory path
    private static final String TEMP_DIRECTORY = System.getProperty("user.dir") + "/tmp";

    public TemporaryFileManager() {
        // Ensure the temp directory exists
        File dir = new File(TEMP_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    /**
     * Save a file temporarily and return the file name.
     *
     * @param file MultipartFile to save
     * @return The generated file name
     * @throws IOException If an error occurs while saving the file
     */
    public String saveFile(MultipartFile file) throws IOException {
        // Generate a unique file name
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(TEMP_DIRECTORY, uniqueFileName);

        // Save the file to the temp directory
        file.transferTo(filePath.toFile());

        return uniqueFileName; // Return the file name for later retrieval
    }

    /**
     * Load a file by its name and return it as a MultipartFile.
     *
     * @param fileName The name of the file to load
     * @return The file as a MultipartFile
     * @throws IOException If an error occurs while reading the file
     */
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

    /**
     * Delete a file by its name.
     *
     * @param fileName The name of the file to delete
     * @return True if the file was successfully deleted, false otherwise
     */
    public boolean deleteFile(String fileName) {
        Path filePath = Paths.get(TEMP_DIRECTORY, fileName);
        File file = filePath.toFile();

        if (file.exists()) {
            return file.delete(); // Attempt to delete the file
        }

        return false; // File did not exist
    }
}
