package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.tournament.domain.service.TournamentTypeService;
import esgi.ascl.tournament.infrastructure.web.request.TournamentTypeRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/tournament-category")
public class TournamentTypeController {
    private final TournamentTypeService tournamentTypeService;

    public TournamentTypeController(TournamentTypeService tournamentTypeService) {
        this.tournamentTypeService = tournamentTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<TournamentTypeResponse> createTournamentType(@RequestBody TournamentTypeRequest tournamentTypeRequest) {
        if (tournamentTypeRequest.getName() == null || tournamentTypeRequest.getName().isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(new TournamentTypeResponse());
    }
}
