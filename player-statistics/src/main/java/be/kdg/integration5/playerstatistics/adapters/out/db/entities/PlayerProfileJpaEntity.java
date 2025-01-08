package be.kdg.integration5.playerstatistics.adapters.out.db.entities;


import be.kdg.integration5.playerstatistics.domain.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="players")
public class PlayerProfileJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(name="username")
    private String userName;


    @Column(name="first_name")
    private String firstName;


    @Column(name="last_name")
    private String lastName;


    @Column(name="birth_date")
    private LocalDate birthDate;



    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationJpaEntity location;

    public PlayerProfileJpaEntity() {
    }

    public PlayerProfileJpaEntity(UUID id, String userName, String firstName, String lastName, LocalDate birthDate, Gender gender, LocationJpaEntity location) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
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

    public LocationJpaEntity getLocation() {
        return location;
    }

    public void setLocation(LocationJpaEntity location) {
        this.location = location;
    }
}
