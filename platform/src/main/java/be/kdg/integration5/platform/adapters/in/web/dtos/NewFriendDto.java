package be.kdg.integration5.platform.adapters.in.web.dtos;

import java.util.UUID;

public class NewFriendDto {
    private UUID friendId;

    public NewFriendDto() {
    }

    public NewFriendDto(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }
}
