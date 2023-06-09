package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.GameType;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.domain.service.PlayService;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.pool.domain.service.PoolService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.utils.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinalPhaseService {
    private final GameService gameService;
    private final TeamService teamService;
    private final PlayService playService;
    private final PoolService poolService;
    private final TournamentService tournamentService;

    public FinalPhaseService(GameService gameService, TeamService teamService, PlayService playService, PoolService poolService, TournamentService tournamentService) {
        this.gameService = gameService;
        this.teamService = teamService;
        this.playService = playService;
        this.poolService = poolService;
        this.tournamentService = tournamentService;
    }

    public Tournament nextRound(Tournament tournament){
        List<Long> qualifiedTeams = getQualifiedTeams(tournament)
                .stream()
                .map(Team::getId)
                .toList();

        if(qualifiedTeams.size() == 1){
            tournament.setWinner_id(qualifiedTeams.get(0));
            return tournament;
        }

        createFinalPhaseGame(tournament, qualifiedTeams);

        return tournament;
    }


    public void startFinalPhase(Tournament tournament){
        var pools = poolService.getAllByTournament(tournament.getId());

        //Récupérer les équipes qualifiés
        List<Long> teamsQualified = getQualifiedTeams(tournament).stream().map(Team::getId).toList();

        //if(!NumberUtils.isPowerOfTwo(pools.size())){
        if(!NumberUtils.isPowerOfTwo(teamsQualified.size())){
            int nextPowerOfTwo = NumberUtils.nextPowerOfTwo(pools.size());
            int nbTeamsToAdd = nextPowerOfTwo - pools.size();

            //Prendre le ratio du tournoi et enlever les équipes déjà qualifiés
            var tournamentRatio = tournamentService.ratio(tournament);
            var withoutTeamAlreadyQualified = tournamentRatio.keySet()
                    .stream()
                    .filter(key -> !teamsQualified.contains(key))
                    .toList();

            //Ajouter les équipes manquantes
            teamsQualified.addAll(withoutTeamAlreadyQualified.subList(0, nbTeamsToAdd));
        }
        createFinalPhaseGame(tournament, teamsQualified);
    }


    public List<Game> createFinalPhaseGame(Tournament tournament, List<Long> teamsQualified){
        if(teamsQualified.size() == 2){
            var game = gameService.create(tournament, GameType.FINAL);
            playService.playGame(game, teamService.getById(teamsQualified.get(0)));
            playService.playGame(game, teamService.getById(teamsQualified.get(1)));
            return List.of(game);
        }

        List<Game> games = new ArrayList<>();
        int nbGames = teamsQualified.size() / 2;
        for(int i = 0; i < nbGames; i++){
            var game = gameService.create(tournament, GameType.correctType(teamsQualified.size()));
            playService.playGame(game, teamService.getById(teamsQualified.get(i)));
            playService.playGame(game, teamService.getById(teamsQualified.get(i+1)));
            games.add(game);
        }
        return games;
    }

    private List<Team> getQualifiedTeams(Tournament tournament){
        var tournamentGames = gameService.getAllByTournamentId(tournament.getId());

        GameType currFinalPhase = tournamentGames
                .stream()
                .map(Game::getType)
                .max(GameType::compareTo)
                .orElseThrow();

        var gameFiltered = tournamentGames
                .stream()
                .filter(game -> game.getType().equals(currFinalPhase))
                .toList();

        return gameService.getWinners(gameFiltered);
    }

}
