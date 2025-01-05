package be.kdg.integration5.playerstatistics.adapters.out.db.entities;

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

    public LocationJpaEntity() {
    }

    public LocationJpaEntity(UUID id, String city, CountryJpaEntity country) {
        this.id = id;
        this.city = city;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryJpaEntity getCountry() {
        return country;
    }

    public void setCountry(CountryJpaEntity country) {
        this.country = country;
    }
}
