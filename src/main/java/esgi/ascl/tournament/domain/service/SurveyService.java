package esgi.ascl.Tournament.domain.service;

import esgi.ascl.Tournament.domain.Entitie.Survey;
import esgi.ascl.Tournament.domain.exceptions.SurveyNotFoundException;
import esgi.ascl.Tournament.domain.mapper.SurveyMapper;
import esgi.ascl.Tournament.infrastructure.repositories.SurveyRepository;
import esgi.ascl.Tournament.infrastructure.web.request.SurveyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    public SurveyService(SurveyRepository surveyRepository, SurveyMapper surveyMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
    }


    public Survey create(SurveyRequest surveyRequest) {
        return surveyRepository.save(surveyMapper.requestToEntity(surveyRequest));
    }

    public Survey getById(Long id) {
        return surveyRepository
                .findById(id)
                .orElseThrow(() -> new SurveyNotFoundException(id));
    }

    public List<Survey> getAll() {
        return surveyRepository.findAll();
    }

    public List<Survey> getByUserId(Long id) {
        return surveyRepository.findAllByUserId(id);
    }

    public List<Survey> getByTournamentId(Long id) {
        return surveyRepository.findAllByTournamentId(id);
    }
}
