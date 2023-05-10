package esgi.ascl.tournament.domain.entitie;

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
}
