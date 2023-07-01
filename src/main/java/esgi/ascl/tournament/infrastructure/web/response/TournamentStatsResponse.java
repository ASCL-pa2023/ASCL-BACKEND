package esgi.ascl.tournament.infrastructure.web.response;

import esgi.ascl.tournament.domain.entities.ScoreDifference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentStatsResponse {
    private Long tournamentId;
    private Integer nbPointsScored;
    private Integer nbPossiblePoints;
    private Integer percentageOfPointsScored;
    private ScoreDifference teamWithMostPointsDifference;
}
