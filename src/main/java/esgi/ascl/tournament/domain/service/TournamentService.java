package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.entities.GameType;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.pool.domain.service.PoolService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.domain.exceptions.TournamentException;
import esgi.ascl.tournament.domain.exceptions.TournamentNotFoundException;
import esgi.ascl.tournament.domain.mapper.TournamentMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentRepository;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.utils.Levenshtein;
import esgi.ascl.utils.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final Levenshtein levenshtein = new Levenshtein();
    private final PoolService poolService;
    private final GameService gameService;

    public TournamentService(TournamentRepository tournamentRepository, PoolService poolService, GameService gameService) {
        this.tournamentRepository = tournamentRepository;
        this.poolService = poolService;
        this.gameService = gameService;
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

    public List<Tournament> getByTournamentType(TournamentType tournamentType) {
        return tournamentRepository.getTournamentsByTournamentType(tournamentType);
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

    public HashMap<Long, Double> ratio(Tournament tournament){
        var poolTournamentRatio = new HashMap<Long, Double>();
        poolService
            .getAllByTournament(tournament.getId())
            .forEach(pool -> {
                poolTournamentRatio.putAll(poolService.teamsRecapRatio(pool));
            });
        return poolTournamentRatio;
    }

    /*public void finalPhase(Tournament tournament){
        var pools = poolService.getAllByTournament(tournament.getId());

        List<Long> teamsQualified = new ArrayList<>();
        pools.forEach(pool -> {
            teamsQualified.addAll(
                    poolService.getIdsTeamQualified(pool)
            );
        });

        //if(!NumberUtils.isPowerOfTwo(pools.size())){
        if(!NumberUtils.isPowerOfTwo(teamsQualified.size())){
            int nextPowerOfTwo = NumberUtils.nextPowerOfTwo(pools.size());
            int nbTeamsToAdd = nextPowerOfTwo - pools.size();

            //Prendre le ratio du tournoi et enlever les équipes déjà qualifiés
            var tournamentRatio = ratio(tournament);
            var withoutTeamAlreadyQualified = tournamentRatio.keySet()
                    .stream()
                    .filter(key -> !teamsQualified.contains(key))
                    .toList();

            //Ajouter les équipes manquantes
            teamsQualified.addAll(withoutTeamAlreadyQualified.subList(0, nbTeamsToAdd));
        }
        finalPhaseService.createFinalPhaseGame(tournament, teamsQualified);
    }*/
}
