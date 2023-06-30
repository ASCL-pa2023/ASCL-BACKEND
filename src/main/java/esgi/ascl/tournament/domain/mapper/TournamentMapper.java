package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentStatus;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentResponse;

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
                .is_started(tournament.getIs_started())
                .status(tournament.getStatus().toString())
                .build();
    }

    public static Tournament requestToEntity(TournamentRequest request) {
        return new Tournament()
                .setLocation(request.getLocation())
                .setDeadline_inscription_date(request.getDeadline_inscription_date())
                .setEnd_date(request.getEnd_date())
                .setStart_date(request.getStart_date())
                .setType(TournamentType.valueOf(request.getTournamentType()))
                .setPlaces_number(request.getPlaces_number())
                .setStatus(TournamentStatus.NOT_STARTED);
    }

    public static List<TournamentResponse> listEntityToListResponse(List<Tournament> tournaments) {
        List<TournamentResponse> tournamentResponses = new ArrayList<>();
        for (Tournament tournament : tournaments)
            tournamentResponses.add(entityToResponse(tournament));
        return tournamentResponses;
    }
}
