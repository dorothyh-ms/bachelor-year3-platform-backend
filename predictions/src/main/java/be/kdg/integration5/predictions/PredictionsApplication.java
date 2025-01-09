package be.kdg.integration5.predictions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PredictionsApplication {
    private static final Logger log = LoggerFactory.getLogger(PredictionsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PredictionsApplication.class, args);
    }

}
