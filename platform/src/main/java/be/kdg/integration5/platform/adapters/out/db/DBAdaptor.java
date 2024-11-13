package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.UserEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.UserMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.UserRepository;
import be.kdg.integration5.platform.domain.Gender;
import be.kdg.integration5.platform.domain.User;
import be.kdg.integration5.platform.ports.out.UserPort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DBAdaptor implements UserPort {
    private final UserRepository userRepository;

    public DBAdaptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserByName(String username) {
        setTestUser();
        List<User> userList = new ArrayList<>();
        userRepository.findUserEntityByUsernameContainingIgnoreCase(username).forEach(userEntity -> userList.add(UserMapper.toUser(userEntity)));
        return userList;
    }

    private void setTestUser(){
        // Test instance 1
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPlayerId(UUID.randomUUID());
        userEntity1.setUsername("johndoe");
        userEntity1.setLastName("Doe");
        userEntity1.setFirstName("John");
        userEntity1.setAge(25);
        userEntity1.setGender(Gender.MALE);
        userEntity1.setLocation("New York");

        // Test instance 2
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setPlayerId(UUID.randomUUID());
        userEntity2.setUsername("janedoe");
        userEntity2.setLastName("Doe");
        userEntity2.setFirstName("Jane");
        userEntity2.setAge(30);
        userEntity2.setGender(Gender.FEMALE);
        userEntity2.setLocation("Los Angeles");

        // Test instance 3
        UserEntity userEntity3 = new UserEntity();
        userEntity3.setPlayerId(UUID.randomUUID());
        userEntity3.setUsername("alexsmith");
        userEntity3.setLastName("Smith");
        userEntity3.setFirstName("Alex");
        userEntity3.setAge(40);
        userEntity3.setGender(Gender.OTHER);
        userEntity3.setLocation("Chicago");
        userRepository.save(userEntity1);
        userRepository.save(userEntity2);
        userRepository.save(userEntity3);

    }
}
