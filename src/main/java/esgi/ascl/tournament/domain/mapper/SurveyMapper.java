package esgi.ascl.tournament.domain.mapper;

import esgi.ascl.tournament.domain.entities.Survey;

import esgi.ascl.tournament.infrastructure.web.request.SurveyRequest;
import esgi.ascl.tournament.infrastructure.web.response.SurveyResponse;
import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SurveyMapper {

    private final UserService userService;
    private final TournamentService tournamentService;


    public SurveyMapper(UserService userService, TournamentService tournamentService) {
        this.userService = userService;
        this.tournamentService = tournamentService;
    }

    public Survey requestToEntity(SurveyRequest surveyRequest, Long teamId){
        return new Survey()
                .setContent(surveyRequest.content)
                .setUser(userService.getById(surveyRequest.userId))
                .setTournament(tournamentService.getById(surveyRequest.tournamentId))
                .setTeamId(teamId)
                .setCreationDate(new Date());
    }

    public SurveyResponse entityToResponse(Survey survey){
        return new SurveyResponse()
                .setId(survey.getId())
                .setContent(survey.getContent())
                .setUserId(UserMapper.entityToResponse(survey.getUser()))
                .setTournament(survey.getTournament())
                .setCreationDate(survey.getCreationDate());
    }

}
