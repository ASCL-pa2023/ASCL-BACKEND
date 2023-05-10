package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingCategoryMapper;
import esgi.ascl.training.infastructure.repository.TrainingCategoryRepository;
import esgi.ascl.training.infastructure.web.request.TrainingCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TrainingCategoryService {
    private final TrainingCategoryRepository trainingCategoryRepository;

    public TrainingCategoryService(TrainingCategoryRepository trainingCategoryRepository) {
        this.trainingCategoryRepository = trainingCategoryRepository;
    }

    public TrainingCategory getById(Long id) {
        var trainingCategory = trainingCategoryRepository.findById(id);
        return trainingCategory.orElse(null); //equivalent d un ternaire : trainingCategory.isPresent() ? trainingCategory.get() : null;
    }

    public TrainingCategory getByName(String name) {
        return trainingCategoryRepository.getByName(name);
    }

    public TrainingCategory create(TrainingCategoryRequest trainingCategoryRequest) {
        if(trainingCategoryRequest.getName() == null || trainingCategoryRequest.getName().isEmpty())
            return null;
        return trainingCategoryRepository.save(
                TrainingCategoryMapper.requestToEntity(
                        trainingCategoryRequest
                )
        );
    }

    public TrainingCategory update(long id, TrainingCategoryRequest trainingCategoryRequest) {
        var trainingCategory = trainingCategoryRepository.getById(id);
        if(trainingCategoryRequest.getName() != null && !trainingCategoryRequest.getName().isEmpty() && trainingCategory != null)
            if (!Objects.equals(trainingCategoryRequest.getName(), trainingCategory.getName())) {
                trainingCategory.setName(trainingCategoryRequest.getName());
                trainingCategoryRepository.save(trainingCategory);
            }
        return null;
    }

    public void delete(long id) {
        //todo delete les references dans les autres tables
        if(trainingCategoryRepository.existsById(id))
            trainingCategoryRepository.deleteById(id);
    }

    public List<TrainingCategory> getAll() {
        return trainingCategoryRepository.findAll();
    }
}
