package esgi.ascl.Tournament.domain.mapper;

import esgi.ascl.Tournament.domain.Entitie.PartnerCandidacy;
import esgi.ascl.Tournament.domain.service.SurveyService;
import esgi.ascl.Tournament.infrastructure.web.request.PartnerCandidacyRequest;
import esgi.ascl.Tournament.infrastructure.web.response.PartnerCandidacyResponse;
import esgi.ascl.User.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class PartnerCandidacyMapper {
    private final UserService userService;
    private final SurveyService surveyService;

    public PartnerCandidacyMapper(UserService userService, SurveyService surveyService) {
        this.userService = userService;
        this.surveyService = surveyService;
    }

    public PartnerCandidacy requestToEntity(PartnerCandidacyRequest partnerCandidacyRequest) {
        return new PartnerCandidacy()
                .setUser(
                        userService.getById(partnerCandidacyRequest.getUserId())
                )
                .setSurvey(
                        surveyService.getById(partnerCandidacyRequest.getSurveyId())
                );
    }

    public static PartnerCandidacyResponse entityToResponse(PartnerCandidacy partnerCandidacy) {
        return new PartnerCandidacyResponse()
                .setId(partnerCandidacy.getId())
                .setUserId(partnerCandidacy.getUser().getId())
                .setSurveyId(partnerCandidacy.getSurvey().getId());
    }
}
