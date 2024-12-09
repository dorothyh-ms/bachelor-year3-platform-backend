package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="game")
public class GameJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)

    @Column(name = "game_id", updatable = false, nullable = false)
    private UUID id;
    private String name;

    public GameJpaEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public GameJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
