package esgi.ascl.tournament.domain.service;

import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.exceptions.TournamentInscriptionNotFound;
import esgi.ascl.tournament.domain.mapper.TournamentInscriptionMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentInscriptionRepository;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.game.domain.entities.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentInscriptionService {
    private final TournamentInscriptionRepository tournamentInscriptionRepository;
    private final TournamentInscriptionMapper tournamentInscriptionMapper;

    public TournamentInscriptionService(TournamentInscriptionRepository tournamentInscriptionRepository, TournamentInscriptionMapper tournamentInscriptionMapper) {
        this.tournamentInscriptionRepository = tournamentInscriptionRepository;
        this.tournamentInscriptionMapper = tournamentInscriptionMapper;
    }


    public TournamentInscription create(Tournament tournament, Team team) {
        return tournamentInscriptionRepository.save(
                tournamentInscriptionMapper.requestToEntity(tournament, team)
        );
    }

    public TournamentInscription getById(Long id) {
        return tournamentInscriptionRepository
                .findById(id)
                .orElseThrow(() -> new TournamentInscriptionNotFound(id));
    }

    public TournamentInscription getByTournamentIdAndTeamId(Long tournamentId, Long teamId){
        return tournamentInscriptionRepository
                .findByTournamentIdAndTeamId(tournamentId, teamId);
    }

    public List<TournamentInscription> getAll(){
        return  tournamentInscriptionRepository.findAll();
    }

    public List<TournamentInscription> getAllByTournamentId(Long tournamentId){
        return tournamentInscriptionRepository.findAllByTournamentId(tournamentId);
    }

    public List<TournamentInscription> getAllByTeamId(Long teamId){
        return tournamentInscriptionRepository.findAllByTeamId(teamId);
    }


    public void delete(TournamentInscription tournamentInscription){
        tournamentInscription.setTournament(null);
        tournamentInscription.setTeam(null);
        tournamentInscriptionRepository.delete(tournamentInscription);
    }

}
