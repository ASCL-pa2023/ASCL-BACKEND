package esgi.ascl.pool.domain.service;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.pool.domain.exception.PoolException;
import esgi.ascl.pool.infrastructure.PoolRepository;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PoolService {
    private final PoolRepository poolRepository;
    private final TournamentInscriptionService tournamentInscriptionService;

    public PoolService(PoolRepository poolRepository, TournamentInscriptionService tournamentInscriptionService) {
        this.poolRepository = poolRepository;
        this.tournamentInscriptionService = tournamentInscriptionService;
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


    public List<Pool> getAllByTournament(Long tournamentId){
        return poolRepository.findAllByTournamentId(tournamentId);
    }
}
