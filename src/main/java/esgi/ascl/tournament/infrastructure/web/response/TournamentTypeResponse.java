package esgi.ascl.tournament.infrastructure.web.response;

import esgi.ascl.tournament.domain.entitie.TournamentType;

public class TournamentTypeResponse {
    private Long id;
    private String name;

    public TournamentTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TournamentTypeResponse(TournamentType tournamentType) {
        this.id = tournamentType.getId();
        this.name = tournamentType.getName();
    }

    public TournamentTypeResponse() {

    }

    public Long getId() {
        return id;
    }

    public TournamentTypeResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TournamentTypeResponse setName(String name) {
        this.name = name;
        return this;
    }
}
