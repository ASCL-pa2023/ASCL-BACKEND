package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entitie.TournamentType;
import esgi.ascl.tournament.infrastructure.web.response.TournamentTypeResponse;

public class TournamentTypeMapper {

    public static TournamentTypeResponse entityToResponse(TournamentType tournamentType) {
        return new TournamentTypeResponse().builder()
                .id(tournamentType.getId())
                .name(tournamentType.getName())
                .build();
    }
}
