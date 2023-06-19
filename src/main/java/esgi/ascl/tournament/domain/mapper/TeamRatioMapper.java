package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.TeamRatio;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TeamRatioMapper {

    private final TeamMapper teamMapper;
    private final TeamService teamService;

    public TeamRatioMapper(TeamMapper teamMapper, TeamService teamService) {
        this.teamMapper = teamMapper;
        this.teamService = teamService;
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


    public List<TeamRatio> poolRatioToResponse(Map<Long, Double> teamRatio) {
        var res = new ArrayList<TeamRatio>();

        teamRatio.forEach((teamId, ratio) -> {
            var teamRatio1 = new TeamRatio()
                    .setTeam(teamMapper.toResponse(teamService.getById(teamId)))
                    .setRatio(ratio);

            res.add(teamRatio1);
        });
        return res;
    }
}
