package esgi.ascl.pool.infrastructure.web;

import esgi.ascl.pool.domain.service.PoolService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/pool")
public class PoolController {
    private final PoolService poolService;
    private final TournamentService tournamentService;

    public PoolController(PoolService poolService, TournamentService tournamentService) {
        this.poolService = poolService;
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create/{tournamentId}")
    public ResponseEntity<?> create(@PathVariable Long tournamentId){
        Tournament tournament;
        try {
            tournament = tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var pools = poolService.create(tournament);
        return new ResponseEntity<>((pools), HttpStatus.OK);
    }
}
