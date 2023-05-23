package esgi.ascl.tournament.domain.service;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.domain.mapper.TournamentMapper;
import esgi.ascl.tournament.infrastructure.repositories.TournamentRepository;
import esgi.ascl.tournament.infrastructure.web.request.TournamentRequest;
import esgi.ascl.utils.Levenshtein;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final Levenshtein levenshtein = new Levenshtein();

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Tournament getById(Long id) {
        return tournamentRepository.getTournamentById(id);
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
}
