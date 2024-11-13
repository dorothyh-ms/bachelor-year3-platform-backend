package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class User {
    private UUID playerId = UUID.randomUUID();
    private String username;
    private String lastName;
    private String firstName;
    private int age;
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

    public User(UUID playerId, String username, String lastName, String firstName, int age, Gender gender, String location) {
        this.playerId = playerId;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
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
