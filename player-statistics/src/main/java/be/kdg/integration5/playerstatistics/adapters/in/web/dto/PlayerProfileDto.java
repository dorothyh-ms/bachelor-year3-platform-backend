package be.kdg.integration5.playerstatistics.adapters.in.web.dto;

import be.kdg.integration5.playerstatistics.domain.Gender;
import be.kdg.integration5.playerstatistics.domain.Location;

import java.time.LocalDate;
import java.util.UUID;

public class PlayerProfileDto {
    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private Location location;

    public PlayerProfileDto(UUID id, String userName, String firstName, String lastName, LocalDate birthDate, Gender gender, Location location) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
    }

    public PlayerProfileDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
