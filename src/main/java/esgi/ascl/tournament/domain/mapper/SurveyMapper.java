package esgi.ascl.Tournament.domain.mapper;

import esgi.ascl.Tournament.domain.Entitie.Survey;

import esgi.ascl.Tournament.infrastructure.web.request.SurveyRequest;
import esgi.ascl.Tournament.infrastructure.web.response.SurveyResponse;
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

    public Survey requestToEntity(SurveyRequest surveyRequest){
        return new Survey()
                .setContent(surveyRequest.content)
                .setUser(userService.getById(surveyRequest.userId))
                .setTournament(tournamentService.getById(surveyRequest.tournamentId))
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
