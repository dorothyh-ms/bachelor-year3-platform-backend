package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.User;

import java.util.List;

public interface SearchUserUseCase {
    List<User> searchUser(String username);
}
