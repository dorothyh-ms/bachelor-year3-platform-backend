package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.User;
import be.kdg.integration5.platform.ports.in.SearchUserUseCase;
import be.kdg.integration5.platform.ports.out.UserPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserUseCaseImpl implements SearchUserUseCase {
    private final UserPort userPort;

    public SearchUserUseCaseImpl(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public List<User> searchUser(String username) {
        return userPort.getUserByName(username);
    }
}
