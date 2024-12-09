package be.kdg.integration5.adapters.out.db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;


@Entity
@Table(catalog="statistics", name="locations")
public class LocationJpaEntity {


    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    private String city;

    @ManyToOne
    @JoinColumn(name="country_id")
    private CountryJpaEntity country;
}
