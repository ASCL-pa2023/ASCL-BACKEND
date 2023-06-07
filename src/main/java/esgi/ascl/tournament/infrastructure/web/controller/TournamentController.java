package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.mapper.TournamentMapper;
import esgi.ascl.tournament.domain.service.TournamentService;
import esgi.ascl.tournament.domain.service.TournamentTypeService;
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
    private final TournamentTypeService tournamentTypeService;

    public TournamentController(TournamentService tournamentService, TournamentTypeService tournamentTypeService) {
        this.tournamentService = tournamentService;
        this.tournamentTypeService = tournamentTypeService;
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

    @GetMapping("/get/{id}")
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

    @GetMapping("/get-all")
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

    @GetMapping("/get-by-location/{location}")
    public ResponseEntity<List<TournamentResponse>> getTournamentByLocation(@PathVariable String location) {
        System.out.println("/api/v1/tournament/get-by-location/{location}" + location);

        if (location == null || location.isEmpty())
            return ResponseEntity.badRequest().build();
        try {
            var tournaments = tournamentService.getByLocationLevenshtein(location);
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
    public ResponseEntity<List<TournamentResponse>> getTournamentByType(@PathVariable long type) {
        System.out.println("/api/v1/tournament/get-by-type/{type}" + type);

        if (type <= 0)
            return ResponseEntity.badRequest().build();
        try {
            var tournamentType = tournamentTypeService.getById(type);
            if (tournamentType == null)
                return ResponseEntity.notFound().build();


            var tournaments = tournamentService.getByTournamentType(tournamentType);
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

    @PostMapping("/update/{id}")
    public ResponseEntity<TournamentResponse> updateTournament(@PathVariable long id, @RequestBody TournamentRequest tournamentRequest) {
        System.out.println("/api/v1/tournament/update/{id}" + id);

        if (id <= 0 || tournamentRequest == null)
            return ResponseEntity.badRequest().build();
        try {
            var tournament = tournamentService.getById(id);
            if (tournament == null)
                return ResponseEntity.notFound().build();

            var updatedTournament = tournamentService.update(tournamentRequest, id);
            if (updatedTournament == null)
                return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(
                    TournamentMapper.entityToResponse(
                            updatedTournament
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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

    @PostMapping("/final-phase/{id}")
    public ResponseEntity<?> finalPhase(@PathVariable Long id) {
        Tournament tournament;
        try {
            tournament = tournamentService.getById(id);
        } catch (TournamentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        tournamentService.finalPhase(tournament);

        return ResponseEntity.ok().build();
    }
}
