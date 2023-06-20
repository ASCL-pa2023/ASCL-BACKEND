package esgi.ascl.tournament.infrastructure.web.request;

public class PartnerCandidacyRequest {
    public Long userId;
    public Long surveyId;


    public Long getUserId(){
        return userId;
    }

    public PartnerCandidacyRequest setUserId(Long userId){
        this.userId = userId;
        return this;
    }

    public Long getSurveyId(){
        return surveyId;
    }

    public PartnerCandidacyRequest setSurveyId(Long surveyId){
        this.surveyId = surveyId;
        return this;
    }
}
