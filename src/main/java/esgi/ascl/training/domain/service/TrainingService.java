package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.exception.TrainingNotFoundException;
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
        return training.orElseThrow(() -> new TrainingNotFoundException(id));
    }

    public List<Training> getAll() {
        return trainingRepository.findAll();
    }

    public Training create(TrainingRequest trainingRequest) {
        if(trainingRequest.getTimeSlot() == null ||
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
        var training1 = trainingRepository.findById(id).orElseThrow();
        training1.setTrainingCategory(
                trainingRequest.getTrainingCategoryId() == null ?
                                training1.getTrainingCategory() :
                            trainingCategoryService.getById(trainingRequest.getTrainingCategoryId())
        );
        training1.setDate(
                trainingRequest.getDate() == null ?
                        training1.getDate() :
                        trainingRequest.getDate()
        );
        training1.setTimeSlot(
                trainingRequest.getTimeSlot() == null ?
                        training1.getTimeSlot() :
                        trainingRequest.getTimeSlot()
        );
        training1.setIsRecurrent(
                trainingRequest.getIsRecurrent() == null ?
                training1.getIsRecurrent() :
                trainingRequest.getIsRecurrent()
        );
        training1.setDayOfRecurrence(
                trainingRequest.getDayOfRecurrence() == null ?
                        training1.getDayOfRecurrence() :
                        trainingRequest.getDayOfRecurrence()
        );
        training1.setNbPlayerMax(
                trainingRequest.getNbPlayerMax() == null ?
                training1.getNbPlayerMax() :
                trainingRequest.getNbPlayerMax()
        );

        return trainingRepository.save(training1);
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
