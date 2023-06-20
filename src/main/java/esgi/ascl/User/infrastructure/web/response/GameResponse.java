package esgi.ascl.User.infrastructure.web.response;

import esgi.ascl.game.domain.entities.GameType;
import esgi.ascl.game.infra.web.response.TeamResponse;
import org.joda.time.DateTime;

import java.util.List;

public class GameResponse {

    public Long id;
    public Long tournamentId;
    public Long refereeId;
    public DateTime hourly;
    public Long winner_id;
    public GameType type;
    public List<TeamResponse> teams;


    public Long getId() {
        return id;
    }

    public GameResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTournament() {
        return tournamentId;
    }

    public GameResponse setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public Long getRefereeId() {
        return refereeId;
    }

    public GameResponse setRefereeId(Long refereeId) {
        this.refereeId = refereeId;
        return this;
    }

    public DateTime getHourly() {
        return hourly;
    }

    public GameResponse setHourly(DateTime hourly) {
        this.hourly = hourly;
        return this;
    }

    public Long getWinner_id() {
        return winner_id;
    }

    public GameResponse setWinner_id(Long winner_id) {
        this.winner_id = winner_id;
        return this;
    }

    public GameType getType() {
        return type;
    }

    public GameResponse setType(GameType type) {
        this.type = type;
        return this;
    }

    public List<TeamResponse> getTeams() {
        return teams;
    }

    public GameResponse setTeams(List<TeamResponse> teams) {
        this.teams = teams;
        return this;
    }

}
