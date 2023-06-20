package esgi.ascl.training.domain.service;

import esgi.ascl.training.domain.TrainingCategoryException;
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
        return trainingCategory
                .orElseThrow(() -> new TrainingCategoryException("Training Category not found"));
    }

    public TrainingCategory getByName(String name) {
        return trainingCategoryRepository.findByName(name)
                .orElseThrow(() -> new TrainingCategoryException("Training Category not found"));
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

    public TrainingCategory update(Long id, TrainingCategoryRequest trainingCategoryRequest) {
        var trainingCategory = trainingCategoryRepository
                .findById(id)
                .orElseThrow(() -> new TrainingCategoryException("TrainingCategory not found"));

        if (Objects.equals(trainingCategoryRequest.getName(), trainingCategory.getName())) {
            return trainingCategory;
        }

        trainingCategory.setName(trainingCategoryRequest.getName());
        return trainingCategoryRepository.save(trainingCategory);
    }

    public void delete(Long id) {
        //todo delete les references dans les autres tables
        if(trainingCategoryRepository.existsById(id))
            trainingCategoryRepository.deleteById(id);
    }

    public List<TrainingCategory> getAll() {
        return trainingCategoryRepository.findAll();
    }
}
