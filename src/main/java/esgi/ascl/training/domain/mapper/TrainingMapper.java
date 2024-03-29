package esgi.ascl.training.domain.mapper;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import esgi.ascl.training.infastructure.web.response.TrainingResponse;

import java.util.List;

public class TrainingMapper {
    public static TrainingResponse entityToResponse(Training training){
        return new TrainingResponse().builder()
                .id(training.getId())
                .trainingCategoryResponse(
                        TrainingCategoryMapper.entityToResponse(
                                training.getTrainingCategory()
                        )
                )
                .date(training.getDate())
                .timeSlot(training.getTimeSlot())
                .isRecurrent(training.getIsRecurrent())
                .dayOfRecurrence(training.getDayOfRecurrence())
                .nbPlayerMax(training.getNbPlayerMax())
                .build();
    }

    public static Training requestToEntity(TrainingRequest trainingRequest, TrainingCategory trainingCategory) {
        return new Training()
                .setTrainingCategory(trainingCategory)
                .setDate(trainingRequest.getDate())
                .setTimeSlot(trainingRequest.getTimeSlot())
                .setIsRecurrent(trainingRequest.getIsRecurrent())
                .setDayOfRecurrence(trainingRequest.getDayOfRecurrence())
                .setNbPlayerMax(trainingRequest.getNbPlayerMax());
    }

    public static Training updateRequestToEntity(TrainingRequest trainingRequest, Training training, TrainingCategory trainingCategory) {
        return training
                .setTrainingCategory(
                    trainingCategory == null ? training.getTrainingCategory() : trainingCategory
                )
                .setDate(
                        trainingRequest.getDate() == null ? training.getDate() : trainingRequest.getDate()
                )
                .setTimeSlot(
                        trainingRequest.getTimeSlot() == null ? training.getTimeSlot() : trainingRequest.getTimeSlot()
                )
                .setIsRecurrent(
                        trainingRequest.getIsRecurrent() == null ? training.getIsRecurrent() : trainingRequest.getIsRecurrent()
                )
                .setDayOfRecurrence(
                        trainingRequest.getIsRecurrent() &&
                        trainingRequest.getDayOfRecurrence() == null ? training.getDayOfRecurrence() : null)
                .setNbPlayerMax(
                        trainingRequest.getNbPlayerMax() == null ? training.getNbPlayerMax() : trainingRequest.getNbPlayerMax()
                )
                .setRecurrenceTraining(
                        trainingRequest.getIsRecurrent() ? training : null
                );
    }

    public static Training recurrenceUpdateRequestToEntity(TrainingRequest trainingRequest, Training trainingRecurrence, Training initialTraining, TrainingCategory trainingCategory) {
        return trainingRecurrence
                .setTrainingCategory(
                        trainingCategory == null ? trainingRecurrence.getTrainingCategory() : trainingCategory
                )
                .setDate(
                        trainingRequest.getDate() == null ? trainingRecurrence.getDate() : trainingRequest.getDate()
                )
                .setTimeSlot(
                        trainingRequest.getTimeSlot() == null ? trainingRecurrence.getTimeSlot() : trainingRequest.getTimeSlot()
                )
                .setIsRecurrent(
                        trainingRequest.getIsRecurrent() == null ? trainingRecurrence.getIsRecurrent() : trainingRequest.getIsRecurrent()
                )
                .setDayOfRecurrence(
                        trainingRequest.getIsRecurrent() &&
                                trainingRequest.getDayOfRecurrence() == null ? trainingRecurrence.getDayOfRecurrence() : null)
                .setNbPlayerMax(
                        trainingRequest.getNbPlayerMax() == null ? trainingRecurrence.getNbPlayerMax() : trainingRequest.getNbPlayerMax()
                );
    }

    public static List<TrainingResponse> entityListToResponseList(List<Training> trainingList) {
        List<TrainingResponse> trainingResponseList = null;
        for (Training training : trainingList) {
            trainingResponseList.add(
                    entityToResponse(training
                    )
            );
        }
        return trainingResponseList;
    }
}
