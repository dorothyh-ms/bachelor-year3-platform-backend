package be.kdg.integration5.platform.adapters.out.db.entities;

import be.kdg.integration5.common.domain.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="players")
public class PlayerJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "player_id", updatable = false, nullable = false)
    private UUID playerId;

    @Column(name = "username", updatable = false, nullable = false, unique = true)
    private String username;


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



    public PlayerJpaEntity(UUID playerId, String username) {
        this.playerId = playerId;
        this.username = username;

    }

    public PlayerJpaEntity() {
    }

    @Override
    public String toString() {
        return "PlayerJpaEntity{" +
                "playerId=" + playerId +
                ", username='" + username + '\'' +
                '}';
    }
}
