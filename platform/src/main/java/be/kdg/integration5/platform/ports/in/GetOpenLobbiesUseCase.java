package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Lobby;

import java.util.List;

public interface GetOpenLobbiesUseCase {
    public List<Lobby> getOpenLobbies();
}
