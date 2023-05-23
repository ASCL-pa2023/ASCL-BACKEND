package esgi.ascl.tournament.infrastructure.web.response;

public class PartnerCandidacyResponse {
    public Long id;
    public Long userId;
    public Long surveyId;

    public Long getId(){
        return id;
    }

    public PartnerCandidacyResponse setId(Long id){
        this.id = id;
        return this;
    }

    public Long getUserId(){
        return userId;
    }

    public PartnerCandidacyResponse setUserId(Long userId){
        this.userId = userId;
        return this;
    }

    public Long getSurveyId(){
        return surveyId;
    }

    public PartnerCandidacyResponse setSurveyId(Long surveyId){
        this.surveyId = surveyId;
        return this;
    }

}
