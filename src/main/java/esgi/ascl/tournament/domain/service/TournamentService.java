package esgi.ascl.tournament.domain.service;

import esgi.ascl.tournament.domain.entitie.Tournament;
import esgi.ascl.tournament.domain.entitie.TournamentType;
import esgi.ascl.tournament.infrastructure.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Tournament getById(Long id) {
        return tournamentRepository.getTournamentById(id);
    }

    public List<Tournament> getByLocation(String location) {
        return tournamentRepository.getTournamentsByLocation(location);
    }

    public List<Tournament> getByDate(Date date) {
        return tournamentRepository.getTournamentsByDate(date);
    }

    public List<Tournament> getByTournamentType(TournamentType tournamentType) {
        return tournamentRepository.getTournamentsByTournamentType(tournamentType);
    }
}
