package esgi.ascl.game.domain.mapper;

import esgi.ascl.game.domain.entities.Set;
import esgi.ascl.game.infra.web.response.SetResponse;

public class SetMapper {

    public static SetResponse toResponse(Set set){
        return new SetResponse()
                .setId(set.getId())
                .setGameId(set.getGame().getId());
    }
}
