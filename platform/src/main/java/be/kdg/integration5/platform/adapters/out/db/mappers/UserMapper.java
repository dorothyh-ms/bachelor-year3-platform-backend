package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.UserEntity;
import be.kdg.integration5.platform.domain.User;

public class UserMapper {

    // Convert UserEntity to User
    public static User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();
        user.setPlayerId(userEntity.getPlayerId());
        user.setUsername(userEntity.getUsername());
        user.setLastName(userEntity.getLastName());
        user.setFirstName(userEntity.getFirstName());
        user.setAge(userEntity.getAge());
        user.setGender(userEntity.getGender());
        user.setLocation(userEntity.getLocation());

        return user;
    }

    // Convert User to UserEntity
    public static UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPlayerId(user.getPlayerId());
        userEntity.setUsername(user.getUsername());
        userEntity.setLastName(user.getLastName());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setAge(user.getAge());
        userEntity.setGender(user.getGender());
        userEntity.setLocation(user.getLocation());

        return userEntity;
    }
}

