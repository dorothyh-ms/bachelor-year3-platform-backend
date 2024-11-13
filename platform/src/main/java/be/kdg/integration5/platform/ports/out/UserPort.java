package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.User;

import java.util.List;

public interface UserPort {
List<User> getUserByName(String username);
}
