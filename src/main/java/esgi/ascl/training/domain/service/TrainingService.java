package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.exception.TrainingNotFoundException;
import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.infastructure.repository.TrainingRepository;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import esgi.ascl.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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


    public List<Training> getAllRecurrences(Training training) {
        return trainingRepository.findAllByRecurrenceTraining(training);
    }

    public Training create(TrainingRequest trainingRequest) {
        var trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());

        var training = trainingRepository.save(
                TrainingMapper.requestToEntity(trainingRequest, trainingCategory)
        );


        if(trainingRequest.getIsRecurrent()){
            createRecurrences(trainingRequest, training);
        }

        return training;
    }


    public List<Training> createRecurrences(TrainingRequest trainingRequest, Training training){
        var dayOccurrences = DateUtils.geListOfDayOccurrences(
                trainingRequest.getDayOfRecurrence(),
                trainingRequest.getTrainingRecurrenceRequest().getStartDate(),
                trainingRequest.getTrainingRecurrenceRequest().getEndDate()
        );

        var result = new ArrayList<Training>();
        dayOccurrences.forEach(day -> {
            var training1 = new Training()
                    .setTrainingCategory(training.getTrainingCategory())
                    .setDate(day)
                    .setTimeSlot(trainingRequest.getTimeSlot())
                    .setIsRecurrent(true)
                    .setDayOfRecurrence(trainingRequest.getDayOfRecurrence())
                    .setRecurrenceTraining(training)
                    .setNbPlayerMax(trainingRequest.getNbPlayerMax());
            result.add(training1);
        });

        return trainingRepository.saveAll(result);
    }


    public Training update(TrainingRequest trainingRequest, Long id) {
        var training = trainingRepository.findById(id).orElseThrow();
        var trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());

        if(trainingRequest.getIsRecurrent()){
            var trainingsRecurrences = getAllRecurrences(training);
            updateTrainingsRecurrences(trainingsRecurrences, trainingRequest, trainingCategory, training);
        }

        return trainingRepository.save(
                TrainingMapper.updateRequestToEntity(
                        trainingRequest,
                        training,
                        trainingCategory)
        );
    }


    public void updateTrainingsRecurrences(List<Training> trainingRecurrences, TrainingRequest trainingRequest,
                                           TrainingCategory trainingCategory, Training training){
        trainingRecurrences.forEach(currTraining -> {
            if(trainingRequest.getTimeSlot() != training.getTimeSlot())
                currTraining.setTimeSlot(trainingRequest.getTimeSlot());

            if(trainingRequest.getDayOfRecurrence() != training.getDayOfRecurrence())
                currTraining.setDayOfRecurrence(trainingRequest.getDayOfRecurrence());

            if(!Objects.equals(trainingRequest.getTrainingCategoryId(), training.getTrainingCategory().getId()))
                currTraining.setTrainingCategory(trainingCategory);

            trainingRepository.save(currTraining);
        });
    }

    public void delete(Long id) {
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
