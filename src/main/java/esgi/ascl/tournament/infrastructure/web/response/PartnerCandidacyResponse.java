package esgi.ascl.tournament.infrastructure.web.response;

import esgi.ascl.User.infrastructure.web.response.UserResponse;

public class PartnerCandidacyResponse {
    public Long id;
    public UserResponse user;
    public SurveyResponse survey;

    public Long getId(){
        return id;
    }

    public PartnerCandidacyResponse setId(Long id){
        this.id = id;
        return this;
    }

    public UserResponse getUser(){
        return user;
    }

    public PartnerCandidacyResponse setUser(UserResponse user){
        this.user = user;
        return this;
    }

    public SurveyResponse getSurvey(){
        return survey;
    }

    public PartnerCandidacyResponse setSurvey(SurveyResponse survey){
        this.survey = survey;
        return this;
    }
}
