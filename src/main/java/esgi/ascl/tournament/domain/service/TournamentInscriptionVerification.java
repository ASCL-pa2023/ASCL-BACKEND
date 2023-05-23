package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentInscriptionVerification {
    private final TournamentInscriptionService tournamentInscriptionService;
    private final TeamService teamService;

    public TournamentInscriptionVerification(TournamentInscriptionService tournamentInscriptionService, TeamService teamService) {
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.teamService = teamService;
    }

    public Boolean isTeamAlreadyInTournament(Long tournamentId, Long teamId) {
        return tournamentInscriptionService.getByTournamentIdAndTeamId(tournamentId, teamId) != null;
    }

    public Boolean teamIsInGoodTournament(Tournament tournament, Team team) {
        var tournamentType = tournament.getTournamentType().getName();

        return switch (tournamentType) {
            case "SIMPLE" -> teamService.getAllUserByTeam(team.getId()).size() == 1;
            case "DOUBLE" -> teamService.getAllUserByTeam(team.getId()).size() == 2;
            default -> false;
        };
    }

    public Boolean isTournamentFull(Long tournamentId) {
        return null;
    }
}
