package esgi.ascl.tournament.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.game.domain.entities.GameType;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@Table(name = "tournament")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Column(name = "deadline_inscription_date")
    private LocalDateTime deadline_inscription_date;

    @Enumerated(EnumType.STRING)
    private TournamentType type;

    @Column(name = "places_number")
    private int places_number;

    @Column(name = "winner_id")
    private Long winner_id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;


    public long getId() {
        return id;
    }

    public Tournament setId(long id) {
        this.id = id;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Tournament setLocation(String location) {
        this.location = location;
        return this;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public Tournament setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
        return this;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public Tournament setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
        return this;
    }

    public LocalDateTime getDeadline_inscription_date() {
        return deadline_inscription_date;
    }

    public Tournament setDeadline_inscription_date(LocalDateTime deadline_inscription_date) {
        this.deadline_inscription_date = deadline_inscription_date;
        return this;
    }

    public TournamentType getType() {
        return type;
    }

    public Tournament setType(TournamentType type) {
        this.type = type;
        return this;
    }

    public int getPlaces_number() {
        return places_number;
    }

    public Tournament setPlaces_number(int places_number) {
        this.places_number = places_number;
        return this;
    }

    public Long getWinner_id() {
        return winner_id;
    }

    public Tournament setWinner_id(Long winner_id) {
        this.winner_id = winner_id;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public Tournament setDescription(String description) {
        this.description = description;
        return this;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public Tournament setStatus(TournamentStatus status) {
        this.status = status;
        return this;
    }
}
