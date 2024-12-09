package be.kdg.integration5.chatbot.adapters.in.web.dto;

public class QuestionDTO {
private String question;
private String game;

    public QuestionDTO(String question, String game) {
        this.question = question;
        this.game = game;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
