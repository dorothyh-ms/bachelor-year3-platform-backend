package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.InviteAction;

public class InviteActionDTO {
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public InviteActionDTO(String action) {
        this.action = action;
    }

    public InviteActionDTO() {
    }
}
