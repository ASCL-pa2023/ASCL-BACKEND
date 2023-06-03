package esgi.ascl.pool.infrastructure.web;

import esgi.ascl.pool.domain.exception.PoolException;
import esgi.ascl.pool.domain.mapper.PoolMapper;
import esgi.ascl.pool.domain.service.PoolService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/pool")
public class PoolController {
    private final PoolService poolService;
    private final TournamentService tournamentService;
    private final PoolMapper poolMapper;

    public PoolController(PoolService poolService, TournamentService tournamentService, PoolMapper poolMapper) {
        this.poolService = poolService;
        this.tournamentService = tournamentService;
        this.poolMapper = poolMapper;
    }

    @PostMapping("/create/tournament/{tournamentId}")
    public ResponseEntity<?> create(@PathVariable Long tournamentId){
        Tournament tournament;
        try {
            tournament = tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            var pools = poolService.create(tournament);
            return new ResponseEntity<>((pools), HttpStatus.OK);
        } catch (PoolException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/all/{tournamentId}")
    public ResponseEntity<?> getAllByTournament(@PathVariable Long tournamentId){
        try {
            tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var pools = poolService.getAllByTournament(tournamentId)
                .stream()
                .map(poolMapper::entityToResponse)
                .toList();

        return new ResponseEntity<>(pools, HttpStatus.OK);
    }
}
