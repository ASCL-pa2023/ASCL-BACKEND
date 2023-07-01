package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.tournament.domain.entities.TournamentStats;
import esgi.ascl.tournament.infrastructure.web.response.TournamentStatsResponse;
import org.springframework.stereotype.Component;

@Component
public class TournamentStatsMapper {

    private final TeamMapper teamMapper;

    public TournamentStatsMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    public TournamentStatsResponse toResponse(TournamentStats tournamentStats) {
        return TournamentStatsResponse.builder()
            .tournamentId(tournamentStats.getTournamentId())
            .nbPointsScored(tournamentStats.getNbPointsScored())
            .nbPossiblePoints(tournamentStats.getNbPossiblePoints())
            .percentageOfPointsScored(tournamentStats.getPercentageOfPointsScored())
            .teamWithMostPointsDifference(tournamentStats.getTeamWithMostPointsDifference())
            .build();
    }
}
