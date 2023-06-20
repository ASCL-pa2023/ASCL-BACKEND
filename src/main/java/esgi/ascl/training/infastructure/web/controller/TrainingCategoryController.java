package esgi.ascl.training.infastructure.web.controller;

import esgi.ascl.training.domain.exception.TrainingCategoryException;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import esgi.ascl.training.domain.mapper.TrainingCategoryMapper;
import esgi.ascl.training.domain.service.TrainingCategoryService;
import esgi.ascl.training.infastructure.web.request.TrainingCategoryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/training-category")
public class TrainingCategoryController {
    private final TrainingCategoryService trainingCategoryService;

    public TrainingCategoryController(TrainingCategoryService trainingCategoryService) {
        this.trainingCategoryService = trainingCategoryService;
    }

    @PostMapping()
    public ResponseEntity<?> createTrainingCategory(@RequestBody @NonNull TrainingCategoryRequest trainingCategoryRequest) {
        var category = trainingCategoryService.create(trainingCategoryRequest);
        return ResponseEntity.ok(TrainingCategoryMapper.entityToResponse(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainingCategory(@PathVariable long id) {
        TrainingCategory trainingCategory;
        try {
            trainingCategory = trainingCategoryService.getById(id);
        } catch (TrainingCategoryException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(
                TrainingCategoryMapper.entityToResponse(
                        trainingCategory
                )
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getTrainingCategoryByName(@PathVariable @NonNull String name) {
        TrainingCategory trainingCategory;
        try {
            trainingCategory = trainingCategoryService.getByName(name);
        } catch (TrainingCategoryException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(
                TrainingCategoryMapper.entityToResponse(
                        trainingCategory
                )
        );
    }

    @GetMapping()
    public ResponseEntity<?> getAllTrainingCategories() {
        var trainingCategoryList = trainingCategoryService.getAll()
                .stream()
                .map(TrainingCategoryMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(trainingCategoryList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrainingCategory(@PathVariable Long id,
                                                    @NonNull @RequestBody TrainingCategoryRequest trainingCategoryRequest) {
        if(trainingCategoryRequest.getName() == null || trainingCategoryRequest.getName().isEmpty())
            return ResponseEntity.badRequest().body("Training category name is required");

        TrainingCategory trainingCategory;
        try{
            trainingCategory = trainingCategoryService.update(id, trainingCategoryRequest);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(
                TrainingCategoryMapper.entityToResponse(trainingCategory)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainingCategory(@PathVariable @NonNull Long id) {
        try{
            trainingCategoryService.getById(id);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        trainingCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
