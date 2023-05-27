package esgi.ascl.game.infra.web.response;

import esgi.ascl.User.infrastructure.web.response.UserResponse;

import java.util.List;

public class TeamResponse {
    public Long id;
    public Long gameId;
    public List<UserResponse> users;
    public TeamResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public TeamResponse setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public TeamResponse setUsers(List<UserResponse> users) {
        this.users = users;
        return this;
    }
}
