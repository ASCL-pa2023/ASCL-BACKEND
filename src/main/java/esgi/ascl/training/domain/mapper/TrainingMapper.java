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
                .date(training.getDate())
                .trainingCategoryResponse(
                        TrainingCategoryMapper.entityToResponse(
                                training.getTrainingCategory()
                        )
                )
                .build();
    }

    public static Training requestToEntity(TrainingRequest trainingRequest, TrainingCategory trainingCategory) {
        return new Training()
                .setId(trainingRequest.getId())
                .setTrainingCategory(trainingCategory
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
