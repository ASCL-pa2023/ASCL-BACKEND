package esgi.ascl.game.domain.mapper;

import esgi.ascl.game.domain.entities.Score;
import esgi.ascl.game.infra.web.response.ScoreResponse;

public class ScoreMapper {
    public static ScoreResponse toResponse(Score score) {
        return new ScoreResponse()
                .setId(score.getId())
                .setValue(score.getValue())
                .setTeamId(score.getTeam().getId())
                .setSetId(score.getSet().getId());
    }
}
