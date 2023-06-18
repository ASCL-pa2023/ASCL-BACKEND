package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entities.TournamentTypeO;
import esgi.ascl.tournament.infrastructure.web.request.TournamentTypeRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentTypeResponse;

public class TournamentTypeMapper {

    public static TournamentTypeResponse entityToResponse(TournamentTypeO tournamentTypeO) {
        return new TournamentTypeResponse().builder()
                .id(tournamentTypeO.getId())
                .name(tournamentTypeO.getName())
                .build();
    }

    public static TournamentTypeO requestToEntity(TournamentTypeRequest tournamentTypeRequest) {
        return new TournamentTypeO()
                .setName(tournamentTypeRequest.getName());
    }
}
