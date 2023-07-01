package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.mapper.TeamRatioMapper;
import esgi.ascl.tournament.domain.mapper.TournamentMapper;
import esgi.ascl.tournament.domain.mapper.TournamentStatsMapper;
import esgi.ascl.tournament.domain.service.FinalPhaseService;
import esgi.ascl.tournament.domain.service.StatisticsService;
import esgi.ascl.tournament.domain.service.TournamentService;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/tournament")
public class TournamentController {
    private final TournamentService tournamentService;
    private final FinalPhaseService finalPhaseService;
    private final TeamRatioMapper teamRatioMapper;
    private final StatisticsService statisticsService;
    private final TournamentStatsMapper tournamentStatsMapper;

    public TournamentController(TournamentService tournamentService, FinalPhaseService finalPhaseService, TeamRatioMapper teamRatioMapper, StatisticsService statisticsService, TournamentStatsMapper tournamentStatsMapper) {
        this.tournamentService = tournamentService;
        this.finalPhaseService = finalPhaseService;
        this.teamRatioMapper = teamRatioMapper;
        this.statisticsService = statisticsService;
        this.tournamentStatsMapper = tournamentStatsMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<TournamentResponse> createTournament(@RequestBody TournamentRequest tournamentRequest) {
        System.out.println("/api/v1/tournament/create");

        if (tournamentRequest == null)
            return ResponseEntity.badRequest().build();

        try {
            var createdTournament = tournamentService.create(tournamentRequest);
            if (createdTournament == null)
                return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(
                    TournamentMapper.entityToResponse(
                            createdTournament
                    )
            );
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponse> getTournament(@PathVariable long id) {
        System.out.println("/api/v1/tournament/get/{id}" + id);

        if (id <= 0)
            return ResponseEntity.badRequest().build();
        try {
            var tournament = tournamentService.getById(id);
            if (tournament == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    TournamentMapper.entityToResponse(
                            tournament
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<TournamentResponse>> getAllTournament() {
        System.out.println("/api/v1/tournament/get-all");

        try {
            var tournaments = tournamentService.getAll()
                    .stream()
                    .map(TournamentMapper::entityToResponse)
                    .toList();
            return ResponseEntity.ok(tournaments);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<TournamentResponse>> getTournamentByLocation(@PathVariable String location) {
        var tournaments = tournamentService.getByLocationLevenshtein(location)
                .stream()
                .map(TournamentMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }

    @GetMapping("/get-by-date/")
public ResponseEntity<List<TournamentResponse>> getTournamentByDate(@RequestBody String date) throws ParseException {
        System.out.println("/api/v1/tournament/get-by-date/{date}" + date);

        if (date == null || date.isEmpty())
            return ResponseEntity.badRequest().build();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        try {
            var tournaments = tournamentService.getByDate(date1);
            if (tournaments == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    TournamentMapper.listEntityToListResponse(
                            tournaments
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/get-by-type/{type}")
    public ResponseEntity<List<TournamentResponse>> getTournamentByType(@PathVariable String type) {
        System.out.println("/api/v1/tournament/get-by-type/{type}" + type);
        try {
            /*
            var tournamentType = tournamentTypeService.getById(type);
            if (tournamentType == null)
                return ResponseEntity.notFound().build();

             */


            var tournaments = tournamentService.getByTournamentType(type);
            if (tournaments == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    TournamentMapper.listEntityToListResponse(
                            tournaments
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentResponse> updateTournament(@PathVariable long id, @RequestBody TournamentRequest tournamentRequest) {
        try {
            tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        var updatedTournament = tournamentService.update(tournamentRequest, id);

        return ResponseEntity.ok(
                TournamentMapper.entityToResponse(updatedTournament)
        );
    }

    @GetMapping("/{id}/ratio")
    public ResponseEntity<?> getRatio(@PathVariable Long id) {
        try {
            tournamentService.getById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tournamentService.tournamentRatio(id));
    }

    @GetMapping("{id}/final-phase/ratio")
    public ResponseEntity<?> getFinalPhaseRatio(@PathVariable Long id) {
        Tournament tournament;
        try {
            tournament = tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var ratio = finalPhaseService.ratio(tournament);
        return ResponseEntity.ok(teamRatioMapper.toResponse(ratio));
    }

    @GetMapping("{id}/pool/ratio")
    public ResponseEntity<?> getPoolRatio(@PathVariable Long id) {
        try {
            tournamentService.getById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var poolRatio = tournamentService.poolRatio(id);
        return ResponseEntity.ok(teamRatioMapper.poolRatioToResponse(poolRatio));
    }


    @PostMapping("start/{id}")
    public ResponseEntity<?> startTournament(@PathVariable Long id) {
        try {
            tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        try {
            tournamentService.start(id);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start-final-phase/{id}")
    public ResponseEntity<?> finalPhase(@PathVariable Long id) {
        Tournament tournament;
        try {
            tournament = tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        finalPhaseService.startFinalPhase(tournament);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/nextRound")
    public ResponseEntity<?> nextRound(@PathVariable Long id) {
        Tournament tournament;
        try {
            tournament = tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var tournamentUpdated = finalPhaseService.nextRound(tournament);
        if (tournamentUpdated.getWinner_id() != null) {
            return new ResponseEntity<>(TournamentMapper.entityToResponse(tournamentUpdated), HttpStatus.OK);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTournament(@PathVariable long id) {
        try {
            var tournament = tournamentService.getById(id);
            tournamentService.delete(tournament);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}/stats")
    public ResponseEntity<?> getStats(@PathVariable Long id) {
        try {
            var tournament = tournamentService.getById(id);

            var stats = statisticsService.tournamentStats(tournament);

            return ResponseEntity.ok(tournamentStatsMapper.toResponse(stats));
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/stats/players")
    public ResponseEntity<?> getPlayersStats(@PathVariable Long id) {
        try {
            var tournament = tournamentService.getById(id);

            var stats = statisticsService.tournamentTeamsStats(tournament);

            return ResponseEntity.ok(stats);
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
