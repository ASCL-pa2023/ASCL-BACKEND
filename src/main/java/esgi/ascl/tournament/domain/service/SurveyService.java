package esgi.ascl.tournament.domain.service;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.Survey;
import esgi.ascl.tournament.domain.exceptions.SurveyNotFoundException;
import esgi.ascl.tournament.domain.mapper.SurveyMapper;
import esgi.ascl.tournament.infrastructure.repositories.SurveyRepository;
import esgi.ascl.tournament.infrastructure.web.request.SurveyRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final TeamService teamService;

    public SurveyService(SurveyRepository surveyRepository, SurveyMapper surveyMapper, TeamService teamService) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
        this.teamService = teamService;
    }


    public Survey create(SurveyRequest surveyRequest) {
        var team = teamService.createTeam();
        teamService.addUsers(
                team.getId(),
                new HashSet<>(List.of(surveyRequest.userId))
        );

        return surveyRepository.save(
                surveyMapper.requestToEntity(surveyRequest, team.getId())
        );
    }

    public Survey getById(Long id) {
        return surveyRepository
                .findById(id)
                .orElseThrow(() -> new SurveyNotFoundException(id));
    }

    public List<Survey> getAll() {
        return surveyRepository.findAll();
    }

    public List<Survey> getByUserId(Long id) {
        return surveyRepository.findAllByUserId(id);
    }

    public List<Survey> getByTournamentId(Long id) {
        return surveyRepository.findAllByTournamentId(id);
    }
}
