package esgi.ascl.Tournament.infrastructure.web.request;

public class SurveyRequest {
    public String content;
    public Long userId;
    public Long tournamentId;

    public String getContent(){
        return content;
    }

    public SurveyRequest setContent(String content){
        this.content = content;
        return this;
    }

    public Long getUserId(){
        return userId;
    }

    public SurveyRequest setUserId(Long userId){
        this.userId = userId;
        return this;
    }

    public Long getTournamentId(){
        return tournamentId;
    }

    public SurveyRequest setTournamentId(Long tournamentId){
        this.tournamentId = tournamentId;
        return this;
    }
}
