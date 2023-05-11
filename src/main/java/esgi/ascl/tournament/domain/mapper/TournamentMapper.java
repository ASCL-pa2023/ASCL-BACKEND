package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entitie.Tournament;
import esgi.ascl.tournament.infrastructure.web.response.TournamentResponse;

public class TournamentMapper {
    public static TournamentResponse entityToResponse(Tournament tournament) {
        return new TournamentResponse().builder()
                .id(tournament.getId())
                .location(tournament.getLocation())
                .deadline_inscription_date(tournament.getDeadline_inscription_date())
                .end_date(tournament.getEnd_date())
                .start_date(tournament.getStart_date())
                .tournamentType(TournamentTypeMapper.entityToResponse(tournament.getTournamentType()))
                .build();
    }
}
