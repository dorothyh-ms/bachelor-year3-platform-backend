    package be.kdg.integration5.chatbot.adapters.in.web;

    import be.kdg.integration5.chatbot.adapters.in.web.dto.QuestionDTO;
    import be.kdg.integration5.chatbot.ports.in.GetAnswerUseCase;
    import org.aspectj.weaver.ResolvedPointcutDefinition;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.oauth2.jwt.Jwt;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.util.UUID;


    @RestController
    @RequestMapping("/chatbot")
    public class ChatbotController{

        private final GetAnswerUseCase getAnswerUseCase;
        private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotController.class);

        public ChatbotController(GetAnswerUseCase getAnswerUseCase) {
            this.getAnswerUseCase = getAnswerUseCase;
        }

        @GetMapping("/question")
        @PreAuthorize("hasAuthority('player')")
        public ResponseEntity<String> getAnswerOnQuestion(@AuthenticationPrincipal Jwt token, @RequestBody QuestionDTO questionDTO){

            UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
            return new ResponseEntity<>(getAnswerUseCase.getAnswer(questionDTO.getQuestion(), userId, questionDTO.getGame()), HttpStatus.OK);
        }
    }
