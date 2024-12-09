package be.kdg.integration5.adapters.out.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="countries")
public class CountryJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    private String name;
}
