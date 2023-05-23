package esgi.ascl.tournament.infrastructure.web.response;

import esgi.ascl.User.infrastructure.web.response.UserResponse;
import esgi.ascl.tournament.domain.entities.Tournament;

import java.util.Date;

public class SurveyResponse {
    public Long id;
    public String content;
    public UserResponse user;
    public Tournament tournament;
    public Date creationDate;

    public Long getId(){
        return id;
    }

    public SurveyResponse setId(Long id){
        this.id = id;
        return this;
    }

    public String getContent(){
        return content;
    }

    public SurveyResponse setContent(String content){
        this.content = content;
        return this;
    }

    public UserResponse getUser(){
        return user;
    }

    public SurveyResponse setUserId(UserResponse user){
        this.user = user;
        return this;
    }

    public Tournament getTournament(){
        return tournament;
    }

    public SurveyResponse setTournament(Tournament tournament){
        this.tournament = tournament;
        return this;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public SurveyResponse setCreationDate(Date creationDate){
        this.creationDate = creationDate;
        return this;
    }
}
