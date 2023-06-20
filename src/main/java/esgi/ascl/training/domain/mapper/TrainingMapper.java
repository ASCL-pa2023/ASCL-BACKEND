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
