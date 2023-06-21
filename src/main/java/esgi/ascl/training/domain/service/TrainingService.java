package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.exception.TrainingNotFoundException;
import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.infastructure.repository.TrainingRegistrationRepository;
import esgi.ascl.training.infastructure.repository.TrainingRepository;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import esgi.ascl.utils.DateUtils;
import esgi.ascl.utils.Levenshtein;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class TrainingService {
    private final Levenshtein levenshtein = new Levenshtein();
    private final TrainingRepository trainingRepository;
    private final TrainingCategoryService trainingCategoryService;
    private final TrainingRegistrationRepository trainingRegistrationRepository;

    public TrainingService(TrainingRepository trainingRepository, TrainingCategoryService trainingCategoryService, TrainingRegistrationRepository trainingRegistrationRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingCategoryService = trainingCategoryService;
        this.trainingRegistrationRepository = trainingRegistrationRepository;
    }

    public Training getById(Long id) {
        var training = trainingRepository.findById(id);
        return training.orElseThrow(() -> new TrainingNotFoundException(id));
    }

    public List<Training> getAll() {
        return trainingRepository.findAll();
    }


    public List<Training> getAllRecurrences(Training training) {
        var fistPart =  trainingRepository.findAllByRecurrenceTraining(training);
        var secondPart = trainingRepository.findAllByRecurrenceTraining(training.getRecurrenceTraining());

        var result = new ArrayList<Training>();
        result.addAll(fistPart);
        result.addAll(secondPart);

        return result;
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

    public void delete(Training training) {
        var trainingRecurrences = trainingRepository.findAllByRecurrenceTraining(training);

        var recurrenceWithMinimumDate = trainingRecurrences.stream()
                .min(Comparator.comparing(Training::getDate));

        recurrenceWithMinimumDate.ifPresent(value -> trainingRecurrences.forEach(training1 -> {
            training1.setRecurrenceTraining(value);
        }));


        var trainingRegistrations = trainingRegistrationRepository.findAllByTrainingId(training.getId());
        trainingRegistrations.forEach(trainingRegistration -> trainingRegistration.setPlayer(null));
        trainingRegistrationRepository.deleteAll(trainingRegistrations);


        trainingRepository.delete(training);
    }

    public List<Training> getAllByDate(Date date) {
        return trainingRepository.findAllByDate(date);
    }

    public List<Training> getAllByCategoryId(Long trainingCategoryId) {
        return trainingRepository.findAllByTrainingCategoryId(trainingCategoryId);
    }

    public List<Training> getAllByCategoryName(String name) {
        var result = new ArrayList<Training>();
        trainingRepository.findAll().forEach(training -> {
            var levenshteinCalc = levenshtein.calculate(
                    name.toUpperCase(),
                    training.getTrainingCategory().getName().toUpperCase()
            );

            if(levenshteinCalc < 3){
                result.add(training);
            }
        });

        return result;
    }

    public List<Training> getAllByDayOfRecurrence(DayOfWeek dayOfRecurrence) {
        return trainingRepository.findAllByDayOfRecurrence(dayOfRecurrence);
    }

    public List<Training> getAllByDayOfRecurrenceLevenshtein(String dayOfRecurrence) {
        var result = new ArrayList<Training>();
        trainingRepository.findAll().forEach(training -> {
            var levenshteinCalc = levenshtein.calculate(
                    dayOfRecurrence.toUpperCase(),
                    training.getDayOfRecurrence().toString().toUpperCase()
            );

            if(levenshteinCalc < 3){
                result.add(training);
            }
        });

        return result;
    }

    public List<Training> getByTrainingCategoryAndDate(TrainingCategory trainingCategory, Date date) {
        return trainingRepository.getByTrainingCategoryAndDate(trainingCategory, date);
    }

}
