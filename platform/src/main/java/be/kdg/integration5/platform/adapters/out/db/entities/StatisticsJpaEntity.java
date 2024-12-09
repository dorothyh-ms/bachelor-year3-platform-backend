package be.kdg.integration5.platform.adapters.out.db.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(catalog="platform", name = "statistics")
public class StatisticsJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "match_id", updatable = false, nullable = false) // UUID in MySQL stored as binary(16)
    private UUID matchId;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameJpaEntity game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerJpaEntity player;

    @Column(name = "win")
    private int win;

    @Column(name = "loss")
    private int loss;

    @Column(name = "draw")
    private int draw;

    @Column(name = "start_time_date")
    private LocalDateTime startTimeDate;

    @Column(name = "end_time_date")
    private LocalDateTime endTimeDate;

    @Column(name = "number_of_moves")
    private int numberOfMoves;

    @Column(name = "score")
    private int score;

    // Getters and Setters

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }

    public PlayerJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerJpaEntity player) {
        this.player = player;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public LocalDateTime getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(LocalDateTime startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public LocalDateTime getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(LocalDateTime endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
