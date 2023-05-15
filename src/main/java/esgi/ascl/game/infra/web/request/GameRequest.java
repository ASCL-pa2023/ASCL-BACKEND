package esgi.ascl.game.infra.web.request;

import org.joda.time.DateTime;

public class GameRequest {
    public Long tournamentId;
    public Long refereeId;
    public DateTime hourly;


    public GameRequest setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public GameRequest setRefereeId(Long refereeId) {
        this.refereeId = refereeId;
        return this;
    }

    public GameRequest setHourly(DateTime hourly) {
        this.hourly = hourly;
        return this;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public Long getRefereeId() {
        return refereeId;
    }

    public DateTime getHourly() {
        return hourly;
    }

}
