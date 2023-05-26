package esgi.ascl.pool.domain.service;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.pool.domain.entity.Pool;
import esgi.ascl.pool.infrastructure.PoolRepository;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PoolService {
    private final int MAX_PLAYER_PER_POOL = 4;
    private final PoolRepository poolRepository;
    private final TournamentInscriptionService tournamentInscriptionService;

    public PoolService(PoolRepository poolRepository, TournamentInscriptionService tournamentInscriptionService) {
        this.poolRepository = poolRepository;
        this.tournamentInscriptionService = tournamentInscriptionService;
    }

    public List<Pool> create(Tournament tournament) {
        List<Team> registeredTeams = new ArrayList<>();

        tournamentInscriptionService
                .getAllByTournamentId(tournament.getId())
                .forEach(tournamentInscription -> {
                    registeredTeams.add(tournamentInscription.getTeam());
                });

        List<Team> teamInPool = new ArrayList<>();
        List<Pool> pools = new ArrayList<>();

        for (Team team : registeredTeams) {
            if (teamInPool.size() < MAX_PLAYER_PER_POOL) {
                teamInPool.add(team);
            }

            if (teamInPool.size() == MAX_PLAYER_PER_POOL) {
                var pool = new Pool()
                        .setTeams(teamInPool)
                        .setTournament(tournament);
                pools.add(pool);
                teamInPool = new ArrayList<>();
            }
        }

        poolRepository.saveAll(pools);
        return pools;
    }
}
