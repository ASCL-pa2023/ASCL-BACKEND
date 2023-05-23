package esgi.ascl.tournament.domain.service;

import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.PartnerCandidacy;
import esgi.ascl.tournament.domain.exceptions.PartnerCandidacyNotFound;
import esgi.ascl.tournament.domain.mapper.PartnerCandidacyMapper;
import esgi.ascl.tournament.infrastructure.repositories.PartnerCandidateRepository;
import esgi.ascl.tournament.infrastructure.web.request.PartnerCandidacyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerCandidateService {
    private final PartnerCandidateRepository partnerCandidateRepository;
    private final PartnerCandidacyMapper partnerCandidacyMapper;
    private final TeamService teamService;
    private final TournamentInscriptionService tournamentInscriptionService;

    public PartnerCandidateService(PartnerCandidateRepository partnerCandidateRepository, PartnerCandidacyMapper partnerCandidacyMapper, TeamService teamService, TournamentInscriptionService tournamentInscriptionService) {
        this.partnerCandidateRepository = partnerCandidateRepository;
        this.partnerCandidacyMapper = partnerCandidacyMapper;
        this.teamService = teamService;
        this.tournamentInscriptionService = tournamentInscriptionService;
    }

    public PartnerCandidacy create(PartnerCandidacyRequest partnerCandidacyRequest) {
        return partnerCandidateRepository
                .save(partnerCandidacyMapper.requestToEntity(partnerCandidacyRequest));
    }

    public PartnerCandidacy getById(Long id) {
        return partnerCandidateRepository
                .findById(id)
                .orElseThrow(() -> new PartnerCandidacyNotFound(id));
    }

    public PartnerCandidacy getBySurveyIdAndUserId(Long surveyId, Long userId) {
        return partnerCandidateRepository
                .findBySurveyIdAndUserId(surveyId, userId);
    }

    public List<PartnerCandidacy> getAll(){
        return  partnerCandidateRepository.findAll();
    }

    public List<PartnerCandidacy> getAllBySurveyId(Long surveyId){
        return  partnerCandidateRepository.findAllBySurveyId(surveyId);
    }

    public List<PartnerCandidacy> getAllByUserId(Long userId){
        return  partnerCandidateRepository.findAllByUserId(userId);
    }


    public void delete(PartnerCandidacy partnerCandidacy){
        partnerCandidacy.setSurvey(null);
        partnerCandidacy.setUser(null);
        partnerCandidateRepository.delete(partnerCandidacy);
    }

    public void accept(PartnerCandidacy partnerCandidacy){
        var team  = teamService.createTeam();
        teamService.addUser(team.getId(), partnerCandidacy.getUser());
        teamService.addUser(team.getId(), partnerCandidacy.getSurvey().getUser());

        tournamentInscriptionService.create(
                partnerCandidacy.getSurvey().getTournament(),
                team
        );
        delete(partnerCandidacy);
    }

}
