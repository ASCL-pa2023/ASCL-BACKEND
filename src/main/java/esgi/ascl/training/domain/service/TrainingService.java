package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.exception.TrainingNotFoundException;
import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.infastructure.repository.TrainingRegistrationRepository;
import esgi.ascl.training.infastructure.repository.TrainingRepository;
import esgi.ascl.training.infastructure.web.request.DeleteTrainingRequest;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import esgi.ascl.utils.DateUtils;
import esgi.ascl.utils.Levenshtein;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public Training getLastTrainingRecurrence(Long trainingId) {
        var training = getById(trainingId);
        var trainings = getAllRecurrences(training);
        trainings.sort(Comparator.comparing(Training::getDate));
        return trainings.get(trainings.size() - 1);
    }

    public Training create(TrainingRequest trainingRequest) {
        var trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());

        var training = trainingRepository.save(
                TrainingMapper.requestToEntity(trainingRequest, trainingCategory)
        );

        if(trainingRequest.getIsRecurrent()){
            var dayOccurrences = DateUtils.geListOfDayOccurrences(
                    trainingRequest.getDayOfRecurrence(),
                    trainingRequest.getTrainingRecurrenceRequest().getStartDate(),
                    trainingRequest.getTrainingRecurrenceRequest().getEndDate()
            );
            createRecurrences(trainingRequest, training, dayOccurrences);
        }

        return training;
    }


    public List<Training> createRecurrences(TrainingRequest trainingRequest, Training training, List<LocalDate> dayOccurrences){
        var result = new ArrayList<Training>();
        dayOccurrences.forEach(day -> {
            var training1 = new Training()
                    .setTrainingCategory(training.getTrainingCategory())
                    .setDate(day)
                    .setTimeSlot(trainingRequest.getTimeSlot() == null ? training.getTimeSlot() : trainingRequest.getTimeSlot())
                    .setIsRecurrent(true)
                    .setDayOfRecurrence(trainingRequest.getDayOfRecurrence() == null ? training.getDayOfRecurrence() : trainingRequest.getDayOfRecurrence())
                    .setRecurrenceTraining(training)
                    .setNbPlayerMax(trainingRequest.getNbPlayerMax() == null ? training.getNbPlayerMax() : trainingRequest.getNbPlayerMax());
            result.add(training1);
        });

        return trainingRepository.saveAll(result);
    }


    public Training update(TrainingRequest trainingRequest, Long id) {
        var training = trainingRepository.findById(id).orElseThrow();

        TrainingCategory trainingCategory;
        if(trainingRequest.getTrainingCategoryId() != null)
            trainingCategory = trainingCategoryService.getById(trainingRequest.getTrainingCategoryId());
        else
            trainingCategory = training.getTrainingCategory();

        var lastTrainingRecurrence = getLastTrainingRecurrence(training.getId());

        if(trainingRequest.getIsRecurrent()){
            var trainingsRecurrences = getAllRecurrences(training)
                    .stream()
                    .filter(currTraining -> !Objects.equals(currTraining.getId(), training.getId())).toList();
            updateTrainingsRecurrences(trainingsRecurrences, trainingRequest, trainingCategory);


            if(trainingRequest.getTrainingRecurrenceRequest().getEndDate().isAfter(lastTrainingRecurrence.getDate())){
                var dayOccurrences = DateUtils.geListOfDayOccurrences(
                        trainingRequest.getDayOfRecurrence() == null ? training.getDayOfRecurrence() : trainingRequest.getDayOfRecurrence(),
                        lastTrainingRecurrence.getDate().plusDays(1),
                        trainingRequest.getTrainingRecurrenceRequest().getEndDate()
                );
                createRecurrences(trainingRequest, training, dayOccurrences);


            } else if(trainingRequest.getTrainingRecurrenceRequest().getEndDate().isBefore(lastTrainingRecurrence.getDate())){

                var trainingsToDelete = trainingRepository.findAllByRecurrenceTrainingAndDateAfter(
                        training,
                        trainingRequest.getTrainingRecurrenceRequest().getEndDate()
                );

                trainingsToDelete.forEach(currTraining -> {
                    delete(new DeleteTrainingRequest()
                            .setTrainingId(currTraining.getId())
                            .setWithRecurrences(false)
                    );
                });
            }
        }

        return trainingRepository.save(
                TrainingMapper.updateRequestToEntity(
                        trainingRequest,
                        training,
                        trainingCategory)
        );
    }


    public void updateTrainingsRecurrences(List<Training> trainingRecurrences, TrainingRequest trainingRequest,
                                           TrainingCategory trainingCategory){
        List<Training> result = new ArrayList<>();
        trainingRecurrences.forEach(currTraining -> {
            result.add(TrainingMapper.recurrenceUpdateRequestToEntity(
                    trainingRequest,
                    currTraining,
                    currTraining.getRecurrenceTraining(),
                    trainingCategory
            ));
        });

        trainingRepository.saveAll(result);
    }

    public void delete(DeleteTrainingRequest deleteTrainingRequest) {
        var training = getById(deleteTrainingRequest.getTrainingId());

        deleteRegistrations(training.getId());

        if(deleteTrainingRequest.getWithRecurrences()){
            deleteTrainingsRecurrences(training);
        } else if(training.getRecurrenceTraining() != null){
            rematchRecurrences(training);
        }


        training.setTrainingCategory(null);
        trainingRepository.delete(training);
    }


    private void deleteTrainingsRecurrences(Training training){
        List<Training> trainingRecurrences;

        if(training.getRecurrenceTraining() == null){
            trainingRecurrences = trainingRepository.findAllByRecurrenceTraining(training);
            trainingRecurrences.forEach(training1 -> {
                deleteRegistrations(training1.getId());
                training1.setRecurrenceTraining(null).setTrainingCategory(null);
            });
        } else {
            trainingRecurrences = trainingRepository.findAllByRecurrenceTraining(training.getRecurrenceTraining())
                    .stream()
                    .filter(t -> t.getDate().isAfter(training.getDate()))
                    .toList();

            trainingRecurrences.forEach(training1 -> {
                deleteRegistrations(training1.getId());
                training1.setRecurrenceTraining(null).setTrainingCategory(null);
            });
        }
        trainingRepository.deleteAll(trainingRecurrences);
    }

    private void rematchRecurrences(Training training) {
        var trainingRecurrences = trainingRepository.findAllByRecurrenceTraining(training);
        var recurrenceWithMinimumDate = trainingRecurrences.stream()
                .min(Comparator.comparing(Training::getDate));

        recurrenceWithMinimumDate.ifPresent(value -> trainingRecurrences.forEach(training1 -> {
            training1.setRecurrenceTraining(value);
        }));
    }

    private void deleteRegistrations(Long trainingId){
        var trainingRegistrations = trainingRegistrationRepository.findAllByTrainingId(trainingId);
        trainingRegistrations.forEach(trainingRegistration -> {
            trainingRegistration.setPlayer(null);
            trainingRegistration.setTraining(null);
        });
        trainingRegistrationRepository.deleteAll(trainingRegistrations);
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
