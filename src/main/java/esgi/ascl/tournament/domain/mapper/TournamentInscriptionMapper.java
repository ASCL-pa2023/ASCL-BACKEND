package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.tournament.infrastructure.web.response.TournamentInscriptionResponse;
import org.springframework.stereotype.Component;

@Component
public class TournamentInscriptionMapper {
    public TournamentInscription requestToEntity(Tournament tournament, Team team) {
        return new TournamentInscription()
                .setTournament(tournament)
                .setTeam(team);
    }

    public static TournamentInscriptionResponse entityToResponse(TournamentInscription tournamentInscription) {
        return new TournamentInscriptionResponse()
                .setId(tournamentInscription.getId())
                .setTournamentId(tournamentInscription.getTournament().getId())
                .setTeamId(tournamentInscription.getTeam().getId());
    }
}
