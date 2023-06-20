package esgi.ascl.training.domain.mapper;

import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.infastructure.web.request.TrainingCategoryRequest;
import esgi.ascl.training.infastructure.web.response.TrainingCategoryResponse;

import java.util.List;

public class TrainingCategoryMapper {
    public static TrainingCategoryResponse entityToResponse(TrainingCategory trainingCategory){
        return TrainingCategoryResponse.builder()
                .id(trainingCategory.getId())
                .name(trainingCategory.getName())
                .build();
    }

    public static TrainingCategory requestToEntity(TrainingCategoryRequest trainingCategoryRequest){
        return new TrainingCategory()
                .setName(trainingCategoryRequest.getName());
    }

    public static List<TrainingCategoryResponse> entityListToResponseList(List<TrainingCategory> trainingCategoryList) {
        List<TrainingCategoryResponse> trainingCategoryResponseList = null;
        for (TrainingCategory trainingCategory : trainingCategoryList) {
            trainingCategoryResponseList.add(
                    entityToResponse(trainingCategory
                    )
            );
        }
        return trainingCategoryResponseList;
    }
}
