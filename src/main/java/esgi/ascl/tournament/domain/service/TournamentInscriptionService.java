package esgi.ascl.tournament.domain.service;

import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.game.infra.web.response.TeamResponse;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.exceptions.TournamentInscriptionNotFound;
import esgi.ascl.tournament.domain.mapper.TournamentInscriptionMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentInscriptionRepository;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.game.domain.entities.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentInscriptionService {
    private final TournamentInscriptionRepository tournamentInscriptionRepository;
    private final TournamentInscriptionMapper tournamentInscriptionMapper;
    private final TeamService teamService;

    public TournamentInscriptionService(TournamentInscriptionRepository tournamentInscriptionRepository, TournamentInscriptionMapper tournamentInscriptionMapper, TeamService teamService) {
        this.tournamentInscriptionRepository = tournamentInscriptionRepository;
        this.tournamentInscriptionMapper = tournamentInscriptionMapper;
        this.teamService = teamService;
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

    public List<TeamResponse> getAllTeamsByTournamentId(Long tournamentId){
        var res = new ArrayList<TeamResponse>();

        getAllByTournamentId(tournamentId)
                .forEach(registration -> {
                    var users = teamService.getAllUserByTeam(registration.getTeam().getId())
                            .stream().map(UserMapper::entityToResponse).toList();
                    res.add(TeamMapper.toResponse(registration.getTeam(), users));
                });

        return res;
    }


    public void delete(TournamentInscription tournamentInscription){
        tournamentInscription.setTournament(null);
        tournamentInscription.setTeam(null);
        tournamentInscriptionRepository.delete(tournamentInscription);
    }

    public void deleteByTournamentIdAndTeamId(Long tournamentId, Long teamId){
        var tournamentInscription = getByTournamentIdAndTeamId(tournamentId, teamId);
        delete(tournamentInscription);
    }

}
