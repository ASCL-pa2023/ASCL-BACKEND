package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.tournament.domain.entities.PartnerCandidacy;
import esgi.ascl.tournament.domain.exceptions.PartnerCandidacyNotFound;
import esgi.ascl.tournament.domain.exceptions.SurveyNotFoundException;
import esgi.ascl.tournament.domain.mapper.PartnerCandidacyMapper;
import esgi.ascl.tournament.domain.service.PartnerCandidateService;
import esgi.ascl.tournament.domain.service.SurveyService;
import esgi.ascl.User.domain.exceptions.UserNotFoundExceptions;
import esgi.ascl.User.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/partner-candidacy")
public class PartnerCandidacyController {
    private final PartnerCandidateService partnerCandidateService;
    private final SurveyService surveyService;
    private final UserService userService;

    public PartnerCandidacyController(PartnerCandidateService partnerCandidateService, SurveyService surveyService, UserService userService) {
        this.partnerCandidateService = partnerCandidateService;
        this.surveyService = surveyService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        var partnerCandidacy = partnerCandidateService.getAll()
                .stream()
                .map(PartnerCandidacyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(partnerCandidacy, HttpStatus.OK);
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<?> getBySurveyId(@PathVariable Long surveyId){
        try{
            surveyService.getById(surveyId);
        } catch (SurveyNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var partnerCandidacy = partnerCandidateService.getAllBySurveyId(surveyId)
                .stream()
                .map(PartnerCandidacyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(partnerCandidacy, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId){
        try{
            userService.getById(userId);
        } catch (UserNotFoundExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var partnerCandidacy = partnerCandidateService.getAllByUserId(userId)
                .stream()
                .map(PartnerCandidacyMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(partnerCandidacy, HttpStatus.OK);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> accept(@PathVariable Long id){

        PartnerCandidacy partnerCandidacy;
        try {
            partnerCandidacy = partnerCandidateService.getById(id);
        } catch (PartnerCandidacyNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        partnerCandidateService.accept(partnerCandidacy);

        return new ResponseEntity<>("Accept successfully", HttpStatus.OK);
    }


    @PostMapping("/refuse/{id}")
    public ResponseEntity<?> refuse(@PathVariable Long id){
        PartnerCandidacy partnerCandidacy;
        try {
            partnerCandidacy = partnerCandidateService.getById(id);
        } catch (PartnerCandidacyNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        partnerCandidateService.delete(partnerCandidacy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
