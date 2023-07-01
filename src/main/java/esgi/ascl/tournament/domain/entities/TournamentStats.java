package esgi.ascl.tournament.domain.entities;


public class TournamentStats {
    private Long tournamentId;
    private Integer nbPointsScored;
    private Integer nbPossiblePoints;
    private Integer percentageOfPointsScored;
    private ScoreDifference teamWithMostPointsDifference;


    public Long getTournamentId() {
        return tournamentId;
    }

    public TournamentStats setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public Integer getNbPointsScored() {
        return nbPointsScored;
    }

    public TournamentStats setNbPointsScored(Integer nbPointsScored) {
        this.nbPointsScored = nbPointsScored;
        return this;
    }

    public Integer getNbPossiblePoints() {
        return nbPossiblePoints;
    }

    public TournamentStats setNbPossiblePoints(Integer nbPossiblePoints) {
        this.nbPossiblePoints = nbPossiblePoints;
        return this;
    }

    public Integer getPercentageOfPointsScored() {
        return percentageOfPointsScored;
    }

    public TournamentStats setPercentageOfPointsScored(Integer percentageOfPointsScored) {
        this.percentageOfPointsScored = percentageOfPointsScored;
        return this;
    }

    public ScoreDifference getTeamWithMostPointsDifference() {
        return teamWithMostPointsDifference;
    }

    public TournamentStats setTeamWithMostPointsDifference(ScoreDifference teamWithMostPointsDifference) {
        this.teamWithMostPointsDifference = teamWithMostPointsDifference;
        return this;
    }
}
