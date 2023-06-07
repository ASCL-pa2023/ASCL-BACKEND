package esgi.ascl.pool.infrastructure.web;

import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.domain.mapper.GameMapper;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.pool.domain.exception.PoolException;
import esgi.ascl.pool.domain.mapper.PoolMapper;
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
    private final PoolMapper poolMapper;
    private final TeamService teamService;

    public PoolController(PoolService poolService, TournamentService tournamentService, PoolMapper poolMapper, TeamService teamService) {
        this.poolService = poolService;
        this.tournamentService = tournamentService;
        this.poolMapper = poolMapper;
        this.teamService = teamService;
    }

    @PostMapping("/create/tournament/{tournamentId}")
    public ResponseEntity<?> create(@PathVariable Long tournamentId){
        //TODO : vérifier que des pools n'ont pas déjà été créées pour ce tournoi
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

    @GetMapping("/games-won/pool/{poolId}/team/{teamId}")
    public ResponseEntity<?> getGamesWonByTeam(@PathVariable Long poolId, @PathVariable Long teamId){
        Pool pool;
        try {
            pool = poolService.getById(poolId);
        } catch (PoolException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            teamService.getById(teamId);
        } catch (TeamNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var gamesWon = poolService.getGamesWonByTeamInPool(pool, teamId)
                .stream()
                .map(GameMapper::entityToResponse)
                .toList();

        return new ResponseEntity<>(gamesWon, HttpStatus.OK);
    }
}
