package esgi.ascl.tournament.infrastructure.web.request;

public class TournamentTypeRequest {
    private String name;

    public String getName() {
        return name;
    }

    public TournamentTypeRequest setName(String name) {
        this.name = name;
        return this;
    }
}
