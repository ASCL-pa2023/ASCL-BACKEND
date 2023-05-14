package esgi.ascl.tournament.domain.service;

import esgi.ascl.tournament.domain.entitie.TournamentType;
import esgi.ascl.tournament.domain.mapper.TournamentTypeMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentTypeRepository;
import esgi.ascl.tournament.infrastructure.web.request.TournamentTypeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentTypeService {
    private final TournamentTypeRepository tournamentTypeRepository;

    public TournamentTypeService(TournamentTypeRepository tournamentTypeRepository) {
        this.tournamentTypeRepository = tournamentTypeRepository;
    }

    public TournamentType getById(Long id) {
        return tournamentTypeRepository.getTournamentTypeById(id);
    }

    public TournamentType getByName(String name) {
        return tournamentTypeRepository.getTournamentTypeByName(name);
    }

    public TournamentType createTournamentType(TournamentTypeRequest tournamentTypeRequest) {
        if(tournamentTypeRequest.getName() == null || tournamentTypeRequest.getName().isEmpty())
            return null;
        return tournamentTypeRepository.save(
                TournamentTypeMapper.requestToEntity(
                        tournamentTypeRequest
                )
        );
    }

    public TournamentType updateTournamentType(TournamentTypeRequest tournamentTypeRequest, long id) {
        var tournamentType = tournamentTypeRepository.getTournamentTypeById(id);
        if (tournamentType == null)
            return null;


        return tournamentTypeRepository.save(
                TournamentTypeMapper.requestToEntity(
                        tournamentTypeRequest
                )
        );
    }

    public List<TournamentType> getAll() {
        return tournamentTypeRepository.findAll();
    }

    public void deleteTournamentType(long id) {
        if (tournamentTypeRepository.getTournamentTypeById(id) != null)
            tournamentTypeRepository.deleteById(id);
        //todo prendre en compte les liens entre les tables
    }
}
