package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Score;
import esgi.ascl.game.domain.entities.Set;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.domain.service.*;
import esgi.ascl.tournament.domain.entities.*;
import esgi.ascl.tournament.domain.exceptions.TournamentException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {
    private static final int MAX_POINTS_IN_GAME = 41;
    private final TournamentService tournamentService;
    private final TournamentInscriptionService tournamentInscriptionService;
    private final GameService gameService;
    private final SetService setService;
    private final ScoreService scoreService;
    private final PlayService playService;

    public StatisticsService(TournamentService tournamentService, TournamentInscriptionService tournamentInscriptionService, GameService gameService, SetService setService, ScoreService scoreService, PlayService playService) {
        this.tournamentService = tournamentService;
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.gameService = gameService;
        this.setService = setService;
        this.scoreService = scoreService;
        this.playService = playService;
    }


    public TournamentStats tournamentStats(Tournament tournament){
        if(tournament.getStatus() == TournamentStatus.NOT_STARTED)
            return null;

        var tournamentStats = new TournamentStats()
                .setTournamentId(tournament.getId());

        var nbGames = gameService
                .getAllByTournamentId(tournament.getId())
                .size();
        tournamentStats.setNbPossiblePoints(nbGames * MAX_POINTS_IN_GAME);

        var allTournamentScore = getAllScore(tournament);
        tournamentStats.setNbPointsScored(nbPointsScored(allTournamentScore));

        if(tournamentStats.getNbPointsScored() == 0 || tournamentStats.getNbPossiblePoints() == 0)
            tournamentStats.setPercentageOfPointsScored(0);
        else
            tournamentStats.setPercentageOfPointsScored((tournamentStats.getNbPointsScored() * 100) / tournamentStats.getNbPossiblePoints());

        tournamentStats.setTeamWithMostPointsDifference(teamWithMostPointsDifference(tournament));

        return tournamentStats;
    }

    public List<TeamStats> tournamentTeamsStats(Tournament tournament){
        var registeredTeams = tournamentInscriptionService
                .getAllByTournamentId(tournament.getId())
                .stream().map(TournamentInscription::getTeam)
                .toList();

        return registeredTeams
                .stream()
                .map(team -> getTeamStats(team, tournament))
                .toList();
    }


    private List<Score> getAllScore(Tournament tournament){
        var games = gameService.getAllByTournamentId(tournament.getId());

        var sets = new ArrayList<Set>();
        games.forEach(game -> sets.addAll(setService.getAllSetByGameId(game.getId())));

        var scores = new ArrayList<Score>();
        sets.forEach(set -> scores.addAll(scoreService.getAllBySetId(set.getId())));

        return scores;
    }

    private Integer nbPointsScored(List<Score> scores){
        int nbPointsScored = 0;
        for (Score score: scores) {
            nbPointsScored += score.getValue();
        }
        return nbPointsScored;
    }

    private ScoreDifference teamWithMostPointsDifference(Tournament tournament){
        var tournamentTeams = tournamentInscriptionService
                .getAllByTournamentId(tournament.getId())
                .stream().map(TournamentInscription::getTeam)
                .toList();

        var map = new HashMap<Team, Integer>();
        tournamentTeams.forEach(team -> map.put(team, 0));

        for (Team team : tournamentTeams) {
            var gamesWonByTeam = gameService.getGamesPlayByTeamInTournament(team.getId(), tournament.getId());
            for (Game game : gamesWonByTeam) {
                var gameSets = setService.getAllSetByGameId(game.getId());
                for (Set set : gameSets) {
                    var currTeamScore = scoreService.getBySetIdAndTeamId(set.getId(), team.getId());
                    var plays = playService.getPlaysByGameId(game.getId());
                    var opponent = plays
                            .stream()
                            .filter(play -> !play.getTeam().getId().equals(team.getId()))
                            .findFirst()
                            .orElseThrow(() -> new TeamNotFoundException("Opponent not found"))
                            .getTeam();

                    var opponentScore = scoreService.getBySetIdAndTeamId(set.getId(), opponent.getId());

                    Integer scoreDiff = currTeamScore.getValue() - opponentScore.getValue();

                    map.put(team, map.get(team) + scoreDiff);
                }
            }
        }

        var maxDifference = Collections.max(map.values());
        var result = new HashMap<Team, Integer>();
        map.forEach((team, score) -> {
            if (Objects.equals(score, maxDifference)) {
                result.put(team, score);
            }
        });

        //

        if (result.size() != 1) {
            var random = result.keySet().toArray()[new Random().nextInt(result.keySet().toArray().length)];
            var teamRandom = (Team) random;

            return new ScoreDifference()
                    .setTeam(teamRandom)
                    .setScoreDifference(result.get(teamRandom));
        }

        return new ScoreDifference()
                .setTeam(result.keySet().stream().findFirst().get())
                .setScoreDifference(result.values().stream().findFirst().get());
    }

    private TeamStats getTeamStats(Team team, Tournament tournament){
        var gameWon = tournamentService.getGamesWonByTeam(tournament.getId(), team.getId());
        var gamePlayed = tournamentService.gamesPlayedByTeam(tournament.getId(), team.getId());

        int nbSetsWon = 0;
        for (Game game : gamePlayed){
            var setsWon = setService.getAllSetByGameId(game.getId())
                    .stream()
                    .filter(set -> set.getWinnerId().equals(team.getId()))
                    .toList()
                    .size();
            nbSetsWon += setsWon;
        }

        return new TeamStats()
                .setTeam(team)
                .setTournamentId(tournament.getId())
                .setWins(tournamentService.getGamesWonByTeam(tournament.getId(), team.getId()).size())
                .setLosses(gamePlayed.size() - gameWon.size())
                .setSetsWon(nbSetsWon);
    }
}
