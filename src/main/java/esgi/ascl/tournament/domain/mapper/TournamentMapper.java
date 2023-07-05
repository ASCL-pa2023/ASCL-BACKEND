package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentStatus;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentResponse;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TournamentMapper {
    public static TournamentResponse entityToResponse(Tournament tournament) {
        return new TournamentResponse().builder()
                .id(tournament.getId())
                .location(tournament.getLocation())
                .deadline_inscription_date(tournament.getDeadline_inscription_date())
                .end_date(tournament.getEnd_date())
                .start_date(tournament.getStart_date())
                .tournamentType(tournament.getType().toString())
                .places_number(tournament.getPlaces_number())
                .winner_id(tournament.getWinner_id())
                .description(tournament.getDescription())
                .status(tournament.getStatus().toString())
                .build();
    }

    public static Tournament requestToEntity(TournamentRequest request) {
        var deadline_inscription_date = LocalDateTime.parse(request.getDeadline_inscription_date());
        var start_date = LocalDateTime.parse(request.getStart_date());
        var end_date = LocalDateTime.parse(request.getEnd_date());

        return new Tournament()
                .setLocation(request.getLocation())
                .setDeadline_inscription_date(deadline_inscription_date)
                .setEnd_date(end_date)
                .setStart_date(start_date)
                .setType(TournamentType.valueOf(request.getTournamentType()))
                .setPlaces_number(request.getPlaces_number())
                .setDescription(request.getDescription())
                .setStatus(TournamentStatus.NOT_STARTED);
    }

    public static List<TournamentResponse> listEntityToListResponse(List<Tournament> tournaments) {
        List<TournamentResponse> tournamentResponses = new ArrayList<>();
        for (Tournament tournament : tournaments)
            tournamentResponses.add(entityToResponse(tournament));
        return tournamentResponses;
    }
}
