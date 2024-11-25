package be.kdg.integration5.chatbot.domain;

public class Chatbot {

    private String question;
    private String answer;

    public Chatbot() {}

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
