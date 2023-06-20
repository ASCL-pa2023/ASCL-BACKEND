package esgi.ascl.game.infra.web.request;

public class AssignRefereeRequest {
    private Long gameId;
    private Long refereeId;

    public AssignRefereeRequest(Long gameId, Long refereeId) {
        this.gameId = gameId;
        this.refereeId = refereeId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getRefereeId() {
        return refereeId;
    }
}
