package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.GameException;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.RefereeIsPlayerException;
import esgi.ascl.game.infra.repository.GameRepository;
import esgi.ascl.pool.domain.entity.Pool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class GameService {
    private final UserService userService;
    private final PlayService playService;
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private final GameRepository gameRepository;

    public GameService(UserService userService, PlayService playService, TeamService teamService, UserTeamService userTeamService, GameRepository gameRepository) {
        this.userService = userService;
        this.playService = playService;
        this.teamService = teamService;
        this.userTeamService = userTeamService;
        this.gameRepository = gameRepository;
    }


    /**
     * Create games for each pool
     * @param pools
     */
    public void createGamesPool(List<Pool> pools){
        if(getAllByTournamentId(pools.get(0).getTournament().getId()).size() > 0){
            throw new GameException("Games already created");
        }


        pools.forEach(pool -> {
            //List<Game> poolGames = new ArrayList<>();
            var poolsTeams = pool.getTeams();

            if(poolsTeams.size() > 1){
                for(int i = 0; i < poolsTeams.size(); i++){
                    for(int j = i + 1; j < poolsTeams.size(); j++){
                        var game = gameRepository.save(
                                new Game().setTournament(pool.getTournament())
                        );
                        var play1 = playService.playGame(game, poolsTeams.get(i));
                        var play2 = playService.playGame(game, poolsTeams.get(j));
                        gameRepository.save(game);
                        pool.getGames().add(game);
                        //poolGames.add(game);
                    }
                }
            }
            //pool.setGames(poolGames);
        });
    }


    public Game getById(Long id) {
        return gameRepository.
                findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }
    public List<Game> getAllByTournamentId(Long tournamentId) {
        return gameRepository.findAllByTournamentId(tournamentId);
    }


    public void assignReferee(Long gameId, Long refereeId) {
        User referee = userService.getById(refereeId);
        //TODO: check if referee has referee role

        if(userPlayingGame(refereeId, gameId)){
            throw new RefereeIsPlayerException();
        }
        gameRepository
                .findById(gameId)
                .ifPresentOrElse(
                    game -> {
                        game.setReferee(referee);
                        gameRepository.save(game);
                    },
                    () -> {
                        throw new GameNotFoundException("Game not found");
                    }
                );
    }


    public boolean userPlayingGame(Long userId, Long gameId) {
        AtomicBoolean isPlaying = new AtomicBoolean(false);
        var pl = playService.getPlaysByGameId(gameId);
        pl.forEach(play -> {
            var team = teamService.getById(play.getTeam().getId());
            if (userTeamService.getByUserAndTeam(userId, team.getId()) != null) {
                isPlaying.set(true);
            }
        });
        return isPlaying.get();
    }


    /**
     * Get all players of a game
     * @param gameId : Id of the game
     * @return List<User>
     */
    public List<User> getPlayers(Long gameId) {
        List<User> players = new ArrayList<>();

        var pl = playService.getPlaysByGameId(gameId);
        pl.forEach(play -> {
            var team = teamService.getById(play.getTeam().getId());
            var usersInTeam = userTeamService.getAllByTeam(team.getId());
            usersInTeam.forEach(userTeam -> players.add(userTeam.getUser()));
        });
        return players;
    }

    public List<Team> getTeams(Long gameId) {
        List<Team> teams = new ArrayList<>();

        var pl = playService.getPlaysByGameId(gameId);
        pl.forEach(play -> teams.add(play.getTeam()));
        return teams;
    }


}
