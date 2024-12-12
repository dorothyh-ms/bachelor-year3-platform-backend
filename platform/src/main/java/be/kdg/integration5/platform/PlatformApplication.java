package be.kdg.integration5.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlatformApplication {
    private static final Logger log = LoggerFactory.getLogger(PlatformApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

}
