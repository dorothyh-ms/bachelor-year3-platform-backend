package be.kdg.integration5.platform.adapters.in.web.dtos;

import java.util.UUID;

public class FriendDto {
    private UUID friendId;
    private String friendUsername;

    public FriendDto() {}

    public FriendDto(UUID friendId, String friendUsername) {
        this.friendId = friendId;
        this.friendUsername = friendUsername;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }
}
