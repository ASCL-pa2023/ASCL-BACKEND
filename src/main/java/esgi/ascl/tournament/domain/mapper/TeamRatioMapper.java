package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.tournament.domain.entities.TeamRatio;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TeamRatioMapper {

    private final TeamMapper teamMapper;

    public TeamRatioMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    public List<TeamRatio> toResponse(Map<Team, Double> teamRatio) {
        var res = new ArrayList<TeamRatio>();

        teamRatio.forEach((team, ratio) -> {
            var teamRatio1 = new TeamRatio()
                    .setTeam(teamMapper.toResponse(team))
                    .setRatio(ratio);

            res.add(teamRatio1);
        });
        return res;
    }
}
