package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.file.ExcelFileMapper;
import esgi.ascl.file.TournamentRegistrationWriter;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.infra.web.response.TeamResponse;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.exceptions.TournamentInscriptionNotFound;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.mapper.TournamentInscriptionMapper;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import esgi.ascl.tournament.domain.service.TournamentInscriptionVerification;
import esgi.ascl.tournament.domain.service.TournamentService;
import esgi.ascl.tournament.infrastructure.web.request.TournamentInscriptionRequest;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.infrastructure.web.response.TournamentInscriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/tournament-inscription/")
public class TournamentInscriptionController {
    private final TournamentInscriptionService tournamentInscriptionService;
    private final TournamentInscriptionVerification tournamentInscriptionVerification;
    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final TournamentRegistrationWriter tournamentRegistrationWriter;

    public TournamentInscriptionController(TournamentInscriptionService tournamentInscriptionService, TournamentInscriptionVerification tournamentInscriptionVerification, TournamentService tournamentService, TeamService teamService, TournamentRegistrationWriter tournamentRegistrationWriter) {
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.tournamentInscriptionVerification = tournamentInscriptionVerification;
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.tournamentRegistrationWriter = tournamentRegistrationWriter;
    }



    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody TournamentInscriptionRequest tournamentInscriptionRequest){
        Tournament tournament;
        try {
            tournament = tournamentService.getById(tournamentInscriptionRequest.tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Team team;
        try {
            team = teamService.getById(tournamentInscriptionRequest.teamId);
        } catch (TeamNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (!tournamentInscriptionVerification.teamIsInGoodTournament(tournament, team))
            return new ResponseEntity<>("User in team not match with the tournament type", HttpStatus.BAD_REQUEST);

        if(tournamentInscriptionVerification.isTeamAlreadyInTournament(tournament.getId(), team.getId()))
            return new ResponseEntity<>("You are already registered for this tournament", HttpStatus.BAD_REQUEST);

        var inscription = tournamentInscriptionService.create(tournament, team);
        return new ResponseEntity<>(TournamentInscriptionMapper.entityToResponse(inscription), HttpStatus.OK);
    }


    @GetMapping("all")
    public ResponseEntity<List<TournamentInscriptionResponse>> getAll(){
        var inscriptionResponse = tournamentInscriptionService.getAll()
                .stream()
                .map(TournamentInscriptionMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(inscriptionResponse, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        TournamentInscription  tournamentInscription;
        try {
            tournamentInscription = tournamentInscriptionService.getById(id);
        } catch (TournamentInscriptionNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(TournamentInscriptionMapper.entityToResponse(tournamentInscription), HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}")
    public ResponseEntity<?> getTournamentId(@PathVariable Long tournamentId){
        try {
            tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var inscriptionResponse = tournamentInscriptionService.getAllByTournamentId(tournamentId)
                .stream()
                .map(TournamentInscriptionMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(inscriptionResponse, HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}/teams")
    public ResponseEntity<?> getTeamsRegisteredByTournament(@PathVariable Long tournamentId){
        try {
            tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        List<TeamResponse> teams = tournamentInscriptionService.getAllTeamsByTournamentId(tournamentId);
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}/users")
    public ResponseEntity<?> getUsersRegisteredByTournament(@PathVariable Long tournamentId){
        try {
            tournamentService.getById(tournamentId);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var users = tournamentInscriptionService.getAllUsersByTournamentId(tournamentId)
                .stream()
                .map(UserMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("team/{teamId}")
    public ResponseEntity<?> getByTeamId(@PathVariable Long teamId){
        try {
            teamService.getById(teamId);
        } catch (TeamNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var inscriptionResponse = tournamentInscriptionService.getAllByTeamId(teamId)
                .stream()
                .map(TournamentInscriptionMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(inscriptionResponse, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        TournamentInscription  tournamentInscription;
        try {
            tournamentInscription = tournamentInscriptionService.getById(id);
        } catch (TournamentInscriptionNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        tournamentInscriptionService.delete(tournamentInscription);
        return new ResponseEntity<>("Inscription deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("tournament/{tournamentId}/team/{teamId}")
    public ResponseEntity<?> deleteByTournamentIdAndTeamId(@PathVariable Long tournamentId, @PathVariable Long teamId){
        tournamentInscriptionService.deleteByTournamentIdAndTeamId(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("tournament/{tournamentId}/file")
    public ResponseEntity<?> fillExcel(@PathVariable Long tournamentId){
        try {
            var tournament = tournamentService.getById(tournamentId);
            var file = tournamentRegistrationWriter.excelReview(tournament);

            return new ResponseEntity<>(ExcelFileMapper.toResponse(file), HttpStatus.OK);
        } catch (TournamentNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
