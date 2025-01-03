package be.kdg.integration5;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommenderApplication {
    private static final Logger log = LoggerFactory.getLogger(RecommenderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RecommenderApplication.class, args);
    }


}