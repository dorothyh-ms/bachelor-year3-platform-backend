package be.kdg.integration5.adapters.out.db.entities;


import be.kdg.integration5.domain.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="players")
public class BoardGamePlayerJpaEntity {

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

}
