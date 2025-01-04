package be.kdg.integration5.platform.adapters.in.web.dtos;

public class InviteActionDto {
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public InviteActionDto(String action) {
        this.action = action;
    }

    public InviteActionDto() {
    }
}
