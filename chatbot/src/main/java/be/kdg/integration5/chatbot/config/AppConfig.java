package be.kdg.integration5.chatbot.config;

import be.kdg.integration5.chatbot.adapters.out.PythonFastApiAdapter;
import be.kdg.integration5.chatbot.core.DefaultGetAnswerUseCase;
import be.kdg.integration5.chatbot.ports.in.GetAnswerUseCase;
import be.kdg.integration5.chatbot.ports.out.AnswerLoadPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public GetAnswerUseCase getAnswerUseCase(AnswerLoadPort answerLoadPort) {
        return new DefaultGetAnswerUseCase(answerLoadPort);
    }

    @Bean
    public AnswerLoadPort answerLoadPort() {
        return new PythonFastApiAdapter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
