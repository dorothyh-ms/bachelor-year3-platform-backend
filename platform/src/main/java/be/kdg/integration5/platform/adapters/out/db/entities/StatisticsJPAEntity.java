package be.kdg.integration5.platform.adapters.out.db.entities;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
public class StatisticsJPAEntity {

    @Id
    @Column(name = "match_id", columnDefinition = "BINARY(16)") // UUID in MySQL stored as binary(16)
    private UUID matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", columnDefinition = "BINARY(16)")
    private GameJpaEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", columnDefinition = "BINARY(16)")
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

    @Column(name = "total_time_played")
    private int totalTimePlayed; // Duration in minutes

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

    public int getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void setTotalTimePlayed(int totalTimePlayed) {
        this.totalTimePlayed = totalTimePlayed;
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

    // Method to calculate total time played
    @PrePersist
    @PreUpdate
    public void calculateTotalTimePlayed() {
        if (startTimeDate != null && endTimeDate != null) {
            this.totalTimePlayed = (int) java.time.Duration.between(startTimeDate, endTimeDate).toMinutes();
        }
    }
}
