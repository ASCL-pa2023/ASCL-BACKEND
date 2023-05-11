package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.infastructure.repository.TrainingRepository;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingCategoryService trainingCategoryService;

    public TrainingService(TrainingRepository trainingRepository, TrainingCategoryService trainingCategoryService) {
        this.trainingRepository = trainingRepository;
        this.trainingCategoryService = trainingCategoryService;
    }

    public Training getById(Long id) {
        var training = trainingRepository.findById(id);
        return training.orElse(null);
    }

    public List<Training> getAll() {
        return trainingRepository.findAll();
    }

    public Training create(TrainingRequest trainingRequest) {
        if(trainingRequest.getTimeSlot() == null || trainingRequest.getTimeSlot().isEmpty() ||
            trainingRequest.getDate() == null || trainingRequest.getTrainingCategoryId() <= 0)
            return null;

        var trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());

        return trainingRepository.save(
                TrainingMapper.requestToEntity(
                        trainingRequest,
                        trainingCategory
                )
        );
    }

    public Training update(TrainingRequest trainingRequest, Long id) {
        var training = this.getById(id);
        if (training == null)
            return null;
        var trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());
        return trainingRepository.save(TrainingMapper.requestToEntity(trainingRequest, trainingCategory));
    }

    public void delete(Long id) {
        //todo delete les refs avec dautres tables
        if(trainingRepository.existsById(id))
            trainingRepository.deleteById(id);
    }

    public List<Training> getByDate(Date date) {
        return trainingRepository.getByDate(date);
    }

    public List<Training> getByTrainingCategory(TrainingCategory trainingCategoryId) {
        return trainingRepository.getByTrainingCategory(trainingCategoryId);
    }

    public List<Training> getByTrainingCategoryAndDate(TrainingCategory trainingCategory, Date date) {
        return trainingRepository.getByTrainingCategoryAndDate(trainingCategory, date);
    }

}
