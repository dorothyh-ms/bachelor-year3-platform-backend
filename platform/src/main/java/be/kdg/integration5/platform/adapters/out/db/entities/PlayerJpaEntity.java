package be.kdg.integration5.platform.adapters.out.db.entities;

import be.kdg.integration5.platform.domain.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="player")
public class PlayerJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "player_id", updatable = false, nullable = false)
    private UUID playerId;

    @Column(name = "username", updatable = false, nullable = false, unique = true)
    private String username;

    @Column(name = "last_name", updatable = false, nullable = false)
    private String lastName;

    @Column(name = "first_name", updatable = false, nullable = false)
    private String firstName;

    //TODO: should be replaced by Birthdate
    private int age;


    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String location;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PlayerJpaEntity(UUID playerId, String username, String lastName, String firstName, int age, Gender gender, String location) {
        this.playerId = playerId;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public PlayerJpaEntity() {
    }

    @Override
    public String toString() {
        return "PlayerJpaEntity{" +
                "playerId=" + playerId +
                ", username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", location='" + location + '\'' +
                '}';
    }
}
