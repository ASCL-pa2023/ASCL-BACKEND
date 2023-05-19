package esgi.ascl.Tournament.infrastructure.web.controller;

import esgi.ascl.Tournament.domain.Entitie.Survey;
import esgi.ascl.Tournament.domain.exceptions.SurveyNotFoundException;
import esgi.ascl.Tournament.domain.mapper.SurveyMapper;
import esgi.ascl.Tournament.domain.service.SurveyService;
import esgi.ascl.Tournament.infrastructure.web.request.SurveyRequest;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/survey")
public class SurveyControllers {
    private final SurveyService surveyService;
    private final SurveyMapper surveyMapper;
    private final UserService userService;
    private final esgi.ascl.tournament.domain.service.TournamentService tournamentService;

    public SurveyControllers(SurveyService surveyService, SurveyMapper surveyMapper, UserService userService, TournamentService tournamentService) {
        this.surveyService = surveyService;
        this.surveyMapper = surveyMapper;
        this.userService = userService;
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SurveyRequest surveyRequest){
        if (userService.getById(surveyRequest.userId) == null)
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        if (tournamentService.getById(surveyRequest.tournamentId) == null)
            return new ResponseEntity<>("Tournament not found", HttpStatus.NOT_FOUND);

        var survey = surveyService.create(surveyRequest);

        return new ResponseEntity<>(surveyMapper.entityToResponse(survey), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Survey survey;
        try {
            survey = surveyService.getById(id);
        } catch (SurveyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(surveyMapper.entityToResponse(survey), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        var surveys = surveyService.getAll()
                .stream()
                .map(surveyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId){
        if(userService.getById(userId) == null)
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var surveys = surveyService.getByUserId(userId)
                .stream()
                .map(surveyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<?> getByTournamentId(@PathVariable Long tournamentId){
        if(tournamentService.getById(tournamentId) == null)
            return new ResponseEntity<>("Tournament not found", HttpStatus.NOT_FOUND);

        var surveys = surveyService.getByTournamentId(tournamentId)
                .stream()
                .map(surveyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }
}
