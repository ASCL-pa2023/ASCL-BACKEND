package esgi.ascl.tournament.domain.service;

import com.amazonaws.annotation.Beta;
import esgi.ascl.tournament.domain.entitie.TournamentType;
import esgi.ascl.tournament.infrastructure.repositories.TournamentTypeRepository;
import org.springframework.stereotype.Service;

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

    public TournamentType createTournamentType(TournamentType tournamentType) {
        return tournamentTypeRepository.save(tournamentType);
    }
}
