package esgi.ascl.pool.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.PlayService;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.pool.domain.exception.PoolException;
import esgi.ascl.pool.infrastructure.PoolRepository;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoolService {
    private final PoolRepository poolRepository;
    private final TournamentInscriptionService tournamentInscriptionService;
    private final PlayService playService;
    private final TeamService teamService;

    public PoolService(PoolRepository poolRepository, TournamentInscriptionService tournamentInscriptionService, PlayService playService, TeamService teamService) {
        this.poolRepository = poolRepository;
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.playService = playService;
        this.teamService = teamService;
    }


    public List<Pool> create(Tournament tournament){
        List<Team> registeredTeams = new ArrayList<>();
        tournamentInscriptionService
                .getAllByTournamentId(tournament.getId())
                .forEach(tournamentInscription -> {
                    registeredTeams.add(tournamentInscription.getTeam());
                });
        Collections.shuffle(registeredTeams);

        int nbPools;
        try {
            nbPools = calculateNbPools(registeredTeams.size());
        } catch (PoolException e) {
            throw new PoolException(e.getMessage());
        }
        int[] teamsPerPool = dispatchPlayers(registeredTeams.size(), nbPools);
        
        List<Team> teamInPool = new ArrayList<>();
        List<Pool> pools = new ArrayList<>();

        int teamIndex = 0;
        for (int poolIndex = 0; poolIndex < nbPools; poolIndex++) {
            int nbTeamsInPool = teamsPerPool[poolIndex];

            for (int i = 0; i < nbTeamsInPool; i++) {
                teamInPool.add(registeredTeams.get(teamIndex));
                teamIndex++;
            }

            var pool = new Pool()
                    .setTeams(teamInPool)
                    .setTournament(tournament);
            pools.add(pool);
            teamInPool = new ArrayList<>();
        }
        return poolRepository.saveAll(pools);
    }
    
    
    private int calculateNbPools(int nbRegisteredTeams){
        if (nbRegisteredTeams < 3) {
            throw new PoolException("Pas assez de joueurs pour former une poule");
        }

        int nbPools = 0;

        // Essayer de former le maximum de poules de 3, 4, 5, 6 et 7 joueurs dans cet ordre
        for (int poolSize = 3; poolSize <= 7; poolSize++) {
            int nbPoolsPossibles = nbRegisteredTeams / poolSize;

            if (nbPoolsPossibles > 0) {
                nbPools = nbPoolsPossibles;
                break; // Sortir de la boucle dès qu'une solution est trouvée
            }
        }
        return nbPools;
    }

    private int[] dispatchPlayers(int nbRegisteredTeams, int nbPools) {
        int[] teamsPerPool = new int[nbPools];

        int teamsPerPoolMin = nbRegisteredTeams / nbPools;
        int joueursRestants = nbRegisteredTeams % nbPools;

        for (int i = 0; i < nbPools; i++) {
            teamsPerPool[i] = teamsPerPoolMin;

            if (joueursRestants > 0) {
                teamsPerPool[i]++;
                joueursRestants--;
            }
        }

        return teamsPerPool;
    }


    public Pool getById(Long id){
        return poolRepository
                .findById(id)
                .orElseThrow(() -> new PoolException("Pool not found"));
    }

    public List<Pool> getAllByTournament(Long tournamentId){
        return poolRepository.findAllByTournamentId(tournamentId);
    }


    public List<Game> getGamesWonByTeamInPool(Pool pool, Long teamId){
        return pool.getGames()
                .stream()
                .filter(game -> Objects.equals(game.getWinner_id(), teamId))
                .toList();
    }

    public List<Team> getFirstsOfPool(Pool pool){
        List<Team> firsts = new ArrayList<>();
        Map<Long, Integer> poolRecap = poolRecap(pool);
        int max = Collections.max(poolRecap.values());
        poolRecap.forEach((teamId, nbGamesWon) -> {
            if (nbGamesWon == max) {
                firsts.add(teamService.getById(teamId));
            }
        });
        return firsts;
    }


    public List<Team> getSecondsOfPool(Pool pool){
        List<Team> seconds = new ArrayList<>();
        Map<Long, Integer> poolRecap = poolRecap(pool);
        int max = Collections.max(poolRecap.values());
        poolRecap.forEach((teamId, nbGamesWon) -> {
            if (nbGamesWon == max - 1) {
                //seconds.add(teamId);
            }
        });
        return seconds;
    }

    public Map<Long, Integer> poolRecap(Pool pool){
        SortedMap<Long, Integer> poolRecap = new TreeMap<>();
        var teams = pool.getTeams();
        teams.forEach(team -> {
            int nbGamesWon = getGamesWonByTeamInPool(pool, team.getId()).size();
            poolRecap.put(team.getId(), nbGamesWon);
        });
        return poolRecap;
    }

    public SortedMap<Long, Double> teamsRecapRatio(Pool pool){
        SortedMap<Long, Double> poolRecap = new TreeMap<>();
        pool.getTeams().forEach(team -> {
            int nbGamesWon = getGamesWonByTeamInPool(pool, team.getId()).size();
            int nbGamesPlayed = pool.getGames()
                    .stream()
                    .filter(game -> playService.getPlaysByGameId(game.getId())
                    .stream()
                    .anyMatch(play -> play.getTeam().getId().equals(team.getId())))
                    .toList()
                    .size();

            double ratio = (double) nbGamesWon / nbGamesPlayed;
            poolRecap.put(team.getId(), ratio);
        });
        return poolRecap;
    }


    public List<Team> getTeamWithBestRatio(Pool pool){
        List<Team> teams = new ArrayList<>();
        SortedMap<Long, Double> teamsRatio = teamsRecapRatio(pool);
        double max = Collections.max(teamsRatio.values());
        teamsRatio.forEach((teamId, ratio) -> {
            if (ratio == max) {
                teams.add(teamService.getById(teamId));
            }
        });
        return teams;
    }
}
