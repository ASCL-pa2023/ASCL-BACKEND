package esgi.ascl.pool.domain.mapper;

import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.pool.infrastructure.web.response.PoolResponse;
import org.springframework.stereotype.Component;

@Component
public class PoolMapper {
    private final TeamMapper teamMapper;

    public PoolMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    public PoolResponse entityToResponse(Pool pool) {
        return new PoolResponse()
                .setId(pool.getId())
                .setTeams(
                        pool.getTeams()
                                .stream()
                                .map(teamMapper::toResponse)
                                .toList()
                ).setTournamentId(pool.getTournament().getId());
    }
}
