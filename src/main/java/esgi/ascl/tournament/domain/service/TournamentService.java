package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Play;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.domain.service.PlayService;
import esgi.ascl.pool.domain.service.PoolService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.domain.exceptions.TournamentException;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.mapper.TournamentMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentRepository;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.utils.Levenshtein;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentInscriptionService tournamentInscriptionService;
    private final Levenshtein levenshtein = new Levenshtein();
    private final PoolService poolService;
    private final GameService gameService;
    private final PlayService playService;

    public TournamentService(TournamentRepository tournamentRepository, TournamentInscriptionService tournamentInscriptionService, PoolService poolService, GameService gameService, PlayService playService) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.poolService = poolService;
        this.gameService = gameService;
        this.playService = playService;
    }

    public Tournament getById(Long id) {
        return tournamentRepository
                .findById(id)
                .orElseThrow(()-> new TournamentNotFoundException("Tournament not found"));
    }

    public List<Tournament> getByLocationLevenshtein(String location) {
        List<Tournament> tournaments = tournamentRepository.findAll();
        List<Tournament> tournamentsByLocation = null;
        tournaments.forEach(tournament -> {
            if(levenshtein.calculate(location.toUpperCase(), tournament.getLocation().toUpperCase()) < 3){
                tournamentsByLocation.add(tournament);
            }
        });
        return tournamentRepository.getTournamentsByLocation(location);
    }

    public List<Tournament> getByDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 2);
        Date datePlus2Days = calendar.getTime();


        calendar.setTime(date);
        calendar.add(Calendar.DATE, -2);
        Date dateLess2Days = calendar.getTime();

        List<Tournament> tournaments = tournamentRepository.findAll();
        List<Tournament> tournamentsByDate = null;

        tournaments.forEach(tournament -> {
            if(tournament.getStart_date().after(dateLess2Days) && tournament.getEnd_date().before(datePlus2Days)){
                tournamentsByDate.add(tournament);
            }
        });

        return tournamentsByDate;
    }

    public List<Tournament> getByTournamentType(String tournamentType) {
        return tournamentRepository.getTournamentsByType(TournamentType.valueOf(tournamentType.toUpperCase()));
    }
    public Tournament create(TournamentRequest request) {
        if(request == null)
            return null;
        return tournamentRepository.save(TournamentMapper.requestToEntity(request));
    }

    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    public Tournament update(TournamentRequest request, long id) {
        var tournament = tournamentRepository.getTournamentById(id);
        if (tournament == null)
            return null;
        return tournamentRepository.save(TournamentMapper.requestToEntity(request));
    }

    public void start(Long tournamentId) {
        var pools = poolService.getAllByTournament(tournamentId);
        if(pools == null || pools.isEmpty())
            throw new TournamentException("No pools found for tournament : " + tournamentId);

        try {gameService.createGamesPool(pools);}
        catch (Exception e){throw new RuntimeException("Tournament already started");}
    }

    public HashMap<Long, Double> poolRatio(Long tournamentId){
        var poolTournamentRatio = new HashMap<Long, Double>();
        poolService
            .getAllByTournament(tournamentId)
            .forEach(pool -> {
                poolTournamentRatio.putAll(poolService.teamsRecapRatio(pool));
            });
        return poolTournamentRatio;
    }

    public HashMap<Long, Double> tournamentRatio(Long tournamentId){
        List<Team> teams = tournamentInscriptionService.getAllByTournamentId(tournamentId)
                .stream().map(TournamentInscription::getTeam).toList();

        var tournamentRatio = new HashMap<Long, Double>();


        teams.forEach(team -> {
            var gamesWon = getGamesWonByTeam(tournamentId, team.getId());
            var gamesPlayed = gamesPlayedByTeam(tournamentId, team.getId());
            var ratio = (double) gamesWon.size() / gamesPlayed.size();
            tournamentRatio.put(team.getId(), ratio);
        });

        return tournamentRatio;
    }


    public List<Game> getGamesWonByTeam(Long tournamentId, Long teamId){
        return gameService.getAllByTournamentId(tournamentId)
                .stream()
                .filter(game -> Objects.equals(game.getWinner_id(), teamId))
                .toList();
    }

    public List<Game> gamesPlayedByTeam(Long tournamentId, Long teamId){
        List<Game> games = gameService.getAllByTournamentId(tournamentId);

        var plays = new ArrayList<Play>();

        games.forEach(game -> {
            plays.addAll(playService.getPlaysByGameId(game.getId()));
        });

        return plays.stream()
                .filter(play -> Objects.equals(play.getTeam().getId(), teamId))
                .map(Play::getGame)
                .toList();
    }


    public void delete(Tournament tournament) {
        tournament.setType(null);
        tournamentRepository.delete(tournament);
    }
}
