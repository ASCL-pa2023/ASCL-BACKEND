package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.Role;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.GameStatus;
import esgi.ascl.game.domain.entities.GameType;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.GameException;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.RefereeIsPlayerException;
import esgi.ascl.game.infra.repository.GameRepository;
import esgi.ascl.game.infra.web.request.SetRequest;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.tournament.domain.entities.Tournament;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class GameService {
    private final UserService userService;
    private final PlayService playService;
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private final GameRepository gameRepository;
    private final SetService setService;

    public GameService(UserService userService, PlayService playService, TeamService teamService, UserTeamService userTeamService, GameRepository gameRepository, SetService setService) {
        this.userService = userService;
        this.playService = playService;
        this.teamService = teamService;
        this.userTeamService = userTeamService;
        this.gameRepository = gameRepository;
        this.setService = setService;
    }

    public Game create(Tournament tournament, GameType gameType){
        return gameRepository.save(
                new Game()
                        .setTournament(tournament)
                        .setType(gameType)
                        .setStatus(GameStatus.NOT_STARTED)
        );
    }

    public Game changeStatus(Long id, GameStatus status){
        var game = getById(id);
        game.setStatus(status);
        return gameRepository.save(game);
    }

    public void initGame(Game game, Team team1, Team team2){
        playService.playGame(game, team1);
        playService.playGame(game, team2);

        setService.createSet(new SetRequest().setGameId(game.getId()));
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
            var poolsTeams = pool.getTeams();

            if(poolsTeams.size() > 1){
                for(int i = 0; i < poolsTeams.size(); i++){
                    for(int j = i + 1; j < poolsTeams.size(); j++){
                        var game = create(pool.getTournament(), GameType.POOL);

                        var play1 = playService.playGame(game, poolsTeams.get(i));
                        var play2 = playService.playGame(game, poolsTeams.get(j));

                        setService.createSet(new SetRequest().setGameId(game.getId()));

                        pool.getGames().add(game);
                    }
                }
            }
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

        if(referee.getRole() != Role.REFEREE && referee.getRole() != Role.ADMIN){
            throw new GameException("User is not a referee");
        }

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

    public List<Team> getWinners(List<Game> games){
        return games.stream()
                .filter(game -> game.getWinner_id() != null)
                .map(game -> teamService.getById(game.getWinner_id()))
                .toList();
    }

    public Game winGame(Game game, Team team){
        if(!getTeams(game.getId()).contains(team)){
            throw new GameException("Team not in game");
        }

        game.setStatus(GameStatus.FINISHED);
        game.setWinner_id(team.getId());
        return gameRepository.save(game);
    }

    public List<Game> getGamesWonByTeam(List<Game> games, Long teamId){
        return games
                .stream()
                .filter(game -> Objects.equals(game.getWinner_id(), teamId))
                .toList();
    }

    public List<Game> getGamesPlayByTeamInTournament(Long teamId, Long tournamentId){
        return gameRepository.findAllByTournamentId(tournamentId)
                .stream()
                .filter(game -> getTeams(game.getId()).contains(teamService.getById(teamId)))
                .toList();
    }

    public Game forfeitGame(Game game, Team team){
        var teams = getTeams(game.getId());
        if(!teams.contains(team)){
            throw new GameException("Team not in game");
        }

        var opponent = teams.stream()
                .filter(t -> !Objects.equals(t.getId(), team.getId()))
                .findFirst()
                .orElseThrow();

        var sets = setService.getAllSetByGameId(game.getId())
                .stream()
                .filter(set -> set.getWinnerId() == null)
                .map(set -> {
                    set.setWinnerId(opponent.getId());
                    return setService.save(set);
                });


        game.setWinner_id(opponent.getId());
        game.setStatus(GameStatus.FINISHED);
        return gameRepository.save(game);
    }

}
