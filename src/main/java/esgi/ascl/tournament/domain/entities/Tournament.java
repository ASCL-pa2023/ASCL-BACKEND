package esgi.ascl.tournament.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

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
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "deadline_inscription_date")
    private Date deadline_inscription_date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_type_id")
    private TournamentType tournamentType;

    @Column(name = "places_number")
    private int places_number;

    @Column(name = "winner_id")
    private Long winner_id;


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

    public Date getStart_date() {
        return start_date;
    }

    public Tournament setStart_date(Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Tournament setEnd_date(Date end_date) {
        this.end_date = end_date;
        return this;
    }

    public Date getDeadline_inscription_date() {
        return deadline_inscription_date;
    }

    public Tournament setDeadline_inscription_date(Date deadline_inscription_date) {
        this.deadline_inscription_date = deadline_inscription_date;
        return this;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public Tournament setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
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
}
