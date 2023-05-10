package esgi.ascl.tournament.infrastructure.web.request;

import java.util.Date;

public class TournamentRequest {
    private String location;
    private Date start_date;
    private Date end_date;
    private Date deadline_inscription_date;
    private TournamentTypeRequest tournamentType;

    public String getLocation() {
        return location;
    }

    public TournamentRequest setLocation(String location) {
        this.location = location;
        return this;
    }

    public Date getStart_date() {
        return start_date;
    }

    public TournamentRequest setStart_date(Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public TournamentRequest setEnd_date(Date end_date) {
        this.end_date = end_date;
        return this;
    }

    public Date getDeadline_inscription_date() {
        return deadline_inscription_date;
    }

    public TournamentRequest setDeadline_inscription_date(Date deadline_inscription_date) {
        this.deadline_inscription_date = deadline_inscription_date;
        return this;
    }

    public TournamentTypeRequest getTournamentType() {
        return tournamentType;
    }

    public TournamentRequest setTournamentType(TournamentTypeRequest tournamentType) {
        this.tournamentType = tournamentType;
        return this;
    }
}
