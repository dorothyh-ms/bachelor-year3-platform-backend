package be.kdg.integration5.platform.domain;

import be.kdg.integration5.platform.exceptions.InvalidInviteActionException;

public enum InviteAction {
    DECLINE, ACCEPT;

    public static InviteAction fromString(String action) {
        try {
            return InviteAction.valueOf(action);
        } catch (IllegalArgumentException e) {
            throw new InvalidInviteActionException("Invalid action: " + action);
        }
    }
}
