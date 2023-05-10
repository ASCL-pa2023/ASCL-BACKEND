package esgi.ascl.tournament.infrastructure.web.response;

import java.util.Date;

public class TournamentResponse {
    private Long id;
    private String location;
    private Date start_date;
    private Date end_date;
    private Date deadline_inscription_date;
    private TournamentTypeResponse tournamentType;

    public TournamentResponse() {
    }

    public Long getId() {
        return id;
    }

    public TournamentResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public TournamentResponse setLocation(String location) {
        this.location = location;
        return this;
    }

    public Date getStart_date() {
        return start_date;
    }

    public TournamentResponse setStart_date(Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public TournamentResponse setEnd_date(Date end_date) {
        this.end_date = end_date;
        return this;
    }

    public Date getDeadline_inscription_date() {
        return deadline_inscription_date;
    }

    public TournamentResponse setDeadline_inscription_date(Date deadline_inscription_date) {
        this.deadline_inscription_date = deadline_inscription_date;
        return this;
    }

    public TournamentTypeResponse getTournamentType() {
        return tournamentType;
    }

    public TournamentResponse setTournamentType(TournamentTypeResponse tournamentType) {
        this.tournamentType = tournamentType;
        return this;
    }
}
