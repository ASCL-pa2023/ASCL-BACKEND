package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entitie.Tournament;
import esgi.ascl.tournament.infrastructure.web.response.TournamentResponse;

public class TournamentMapper {
    public static TournamentResponse entityToResponse(Tournament tournament) {
        return new TournamentResponse()
                .setId(tournament.getId())
                .setLocation(tournament.getLocation())
                .setDeadline_inscription_date(tournament.getDeadline_inscription_date())
                .setEnd_date(tournament.getEnd_date())
                .setStart_date(tournament.getStart_date())
                .setTournamentType(TournamentTypeMapper.entityToResponse(tournament.getTournamentType()));
    }
}
